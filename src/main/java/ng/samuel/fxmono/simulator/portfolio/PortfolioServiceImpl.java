package ng.samuel.fxmono.simulator.portfolio;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.portfolio.account.AccountService;
import ng.samuel.fxmono.simulator.portfolio.asset.AssetService;
import ng.samuel.fxmono.simulator.portfolio.dto.AccountResponse;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.transaction.TransactionService;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;
import ng.samuel.fxmono.simulator.portfolio.watchlist.WatchlistService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final AssetService assetService;

    private final WatchlistService watchlistService;

    //private final PriceService priceService;


    @Override
    public Portfolio getPortfolio(int user_id) {
        return null;
    }

    @Override
    public Transaction buyAndSell(int userId, TransactionRequest tr) {
        return null;
    }

    @Override
    public TransactionShortSell shortSellAndBuyToCover(int userId, TransactionRequest tr) {
        return null;
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
