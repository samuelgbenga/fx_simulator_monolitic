package ng.samuel.fxmono.simulator.portfolio;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.exception.exceptions.NotEnoughFundException;
import ng.samuel.fxmono.simulator.exception.exceptions.PriceLimitException;
import ng.samuel.fxmono.simulator.portfolio.account.Account;
import ng.samuel.fxmono.simulator.portfolio.account.AccountService;
import ng.samuel.fxmono.simulator.portfolio.asset.Asset;
import ng.samuel.fxmono.simulator.portfolio.asset.AssetService;
import ng.samuel.fxmono.simulator.portfolio.dto.AccountResponse;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionGainLoss;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionType;
import ng.samuel.fxmono.simulator.portfolio.transaction.TransactionService;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;
import ng.samuel.fxmono.simulator.portfolio.watchlist.WatchlistService;
import ng.samuel.fxmono.simulator.stock.price.PriceService;
import ng.samuel.fxmono.simulator.stock.price.dto.ShortQuote;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final AssetService assetService;

    private final WatchlistService watchlistService;

    private final PriceService priceService;


    @Override
    public Portfolio getPortfolio(int user_id) {
        Account account = accountService.findById(user_id);
        List<Asset> assets = assetService.findAllAssets(user_id);

        double totalRealizedGainLoss = 0.0;
        double totalRealizedGainLoss_shortSelling = 0.0;
        double totalUnrealizedGainLoss = 0.0;
        double totalUnrealizedGainLoss_shortSelling = 0.0;
        double shortSellingDeposit = 0.0;
        List<String> symbols = new ArrayList<>();
        HashMap<String, Asset> assetMap = new HashMap<>();

        if(assets.size() > 0) {
            symbols = assets.stream().map(asset -> asset.getSymbol()).toList();
            String symbolString = StringUtils.join(symbols,',');

            // map the assets using the symbol as key to create a normalized object
            assetMap = new HashMap<>();
            for(var asset : assets) {
                assetMap.put(asset.getSymbol(), asset);
                totalRealizedGainLoss += asset.getRealizedGainLoss();
                totalRealizedGainLoss_shortSelling += asset.getRealizedGainLossShortSelling();
            }

            // calculate unrealized the gain/loss using the current quote for each asset
            ShortQuote[] quotes = priceService.fetchShortQuoteList(symbolString);
            for(var q : quotes) {
                Asset asset = assetMap.get(q.getSymbol());
                asset.setCurrentPrice(q.getPrice());
                double unrealized = (q.getPrice() - asset.getAvgCost()) * asset.getShares();
                double unrealizedBorrowed = -(q.getPrice() - asset.getAvgBorrowed()) * asset.getSharesBorrowed();
                shortSellingDeposit += q.getPrice() * asset.getSharesBorrowed();

                asset.setUnrealizedGainLoss(unrealized);
                asset.setUnrealizedGainLossBorrowed(unrealizedBorrowed);

                totalUnrealizedGainLoss += unrealized;
                totalUnrealizedGainLoss_shortSelling += unrealizedBorrowed;
            }
        }

        AccountResponse accountRes = new AccountResponse(account);
        accountRes.setTotalRealizedGainLoss(totalRealizedGainLoss);
        accountRes.setTotalRealizedGainLoss_shortSelling(totalRealizedGainLoss_shortSelling);

        accountRes.setTotalUnrealizedGainLoss(totalUnrealizedGainLoss);
        accountRes.setTotalUnrealizedGainLoss_shortSelling(totalUnrealizedGainLoss_shortSelling);

        // the deposit has to be 150% of all the short sale value
        accountRes.setShortSellingDeposit(shortSellingDeposit * 1.5);

        HashMap<String, String> watchlist = watchlistService.getWatchlist(user_id);
        return new Portfolio(accountRes, symbols, assetMap, watchlist);
    }

    @Override
    public Transaction buyAndSell(int userId, TransactionRequest tr) {
        ShortQuote[] list = priceService.fetchShortQuoteList(tr.getSymbol());
        double currentPrice = list[0].getPrice();
        Portfolio portfolio = getPortfolio(userId);

        this.watchlistService.removeFromList(userId, tr.getSymbol());

        if(tr.getType().equals(TransactionType.BUY.name())) {
            if(currentPrice > tr.getPriceLimit()) throw new PriceLimitException(tr.getType());
            if(!hasEnoughFund(portfolio, tr.getShares() * currentPrice, false)) {
                throw new NotEnoughFundException("FUND");
            }
        } else {
            if(currentPrice < tr.getPriceLimit()) throw new PriceLimitException(tr.getType());
        }

        TransactionGainLoss transactionGainLoss = assetService.updateAsset(userId, currentPrice, tr);

        Transaction transaction = transactionService
                .addNewTransaction(userId, currentPrice, tr, transactionGainLoss);

        accountService.updateAccountFund(userId, currentPrice, tr, transactionGainLoss.getRealizedGainLoss());

        return transaction;
    }

    @Override
    public TransactionShortSell shortSellAndBuyToCover(int userId, TransactionRequest tr) {
        ShortQuote[] list = priceService.fetchShortQuoteList(tr.getSymbol());
        double currentPrice = list[0].getPrice();
        Portfolio portfolio = getPortfolio(userId);

        this.watchlistService.removeFromList(userId, tr.getSymbol());

        if(tr.getType().equals(TransactionType.BUY_TO_COVER.name())) {
            if(currentPrice > tr.getPriceLimit()) throw new PriceLimitException(tr.getType());
        } else {
            if(currentPrice < tr.getPriceLimit()) throw new PriceLimitException(tr.getType());
            if(!hasEnoughFund(portfolio, tr.getShares() * currentPrice, true)) {
                throw new NotEnoughFundException("FUND");
            }
        }

        TransactionGainLoss transactionGainLoss = assetService.updateAsset(userId, currentPrice, tr);

        TransactionShortSell transactionSS = transactionService
                .addNewTransactionShortSell(userId, currentPrice, tr, transactionGainLoss);

        accountService.updateAccountFund(userId, currentPrice, tr, transactionGainLoss.getRealizedGainLoss());

        return transactionSS;
    }

    private boolean hasEnoughFund(Portfolio portfolio, double orderAmount, boolean shortSell) {
        AccountResponse account = portfolio.getAccount();
        double deposit = account.getShortSellingDeposit();
        double fund = account.getFund();
        // all short sale accounts must have 150% of the value of the short sale at
        // the time the sale is initiated. I need to check if there is enough fund when user
        // place "buy" or "sell short" order
        double amount = shortSell ? orderAmount * 1.5 : orderAmount;

        if(fund - deposit - amount < 0) return false;

        return true;
    }
}
