package ng.samuel.fxmono.simulator.portfolio.transaction;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionGainLoss;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionType;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionTemplate;
import ng.samuel.fxmono.simulator.portfolio.transaction.repository.TransactionRepository;
import ng.samuel.fxmono.simulator.portfolio.transaction.repository.TransactionShortSellRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    private final TransactionShortSellRepository transactionShortSellRepository;

    @Override
    public List<Transaction> findByUserIdAndSymbol(int userId, String symbol) {
        return transactionRepository.findByUserIdAndSymbol(userId, symbol);
    }

    @Override
    public void saveTransaction(Transaction transaction) {

        transactionRepository.save(transaction);
    }

    @Override
    public void saveTransactionShortSell(TransactionShortSell transactionSS) {

        transactionShortSellRepository.save(transactionSS);
    }

    @Override
    public Transaction addNewTransaction(int userId, double currentPrice, TransactionRequest tr, TransactionGainLoss transactionGainLoss) {
        boolean buy = tr.getType().equals(TransactionType.BUY.name());

        Transaction transaction = new Transaction();
        transaction.setId(0);
        transaction.setUserId(userId);
        transaction.setSymbol(tr.getSymbol());
        transaction.setBuy(buy);
        transaction.setPricePerShare(currentPrice);
        transaction.setShares(tr.getShares());
        transaction.setRealizedGainLoss(transactionGainLoss.getRealizedGainLoss());
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.setAssetTotalRealizedGainLoss(transactionGainLoss.getAssetTotalRealizedGainLoss());

        saveTransaction(transaction);
        return transaction;
    }

    @Override
    public TransactionShortSell addNewTransactionShortSell(int userId, double currentPrice, TransactionRequest tr, TransactionGainLoss transactionGainLoss) {
        boolean shortSell = tr.getType().equals(TransactionType.SELL_SHORT.name());

        TransactionShortSell transactionSS = new TransactionShortSell();
        transactionSS.setId(0);
        transactionSS.setUserId(userId);
        transactionSS.setSymbol(tr.getSymbol());
        transactionSS.setShortSell(shortSell);
        transactionSS.setPricePerShare(currentPrice);
        transactionSS.setShares(tr.getShares());
        transactionSS.setRealizedGainLoss(transactionGainLoss.getRealizedGainLoss());
        transactionSS.setTimestamp(System.currentTimeMillis());
        transactionSS.setAssetTotalRealizedGainLoss(transactionGainLoss.getAssetTotalRealizedGainLoss());

        saveTransactionShortSell(transactionSS);
        return transactionSS;
    }

    @Override
    public List<? extends TransactionTemplate> getTransactionsByPage(int userId, String symbol, int pageNum, String type) {
        final int ITEM_PER_PAGE = 20;
        int offset = (pageNum - 1) * ITEM_PER_PAGE;

        if(type.equals("long")){
            return transactionRepository.findByPageNum(userId, symbol, offset);
        } else {
            return transactionShortSellRepository.findByPageNum(userId, symbol, offset);
        }
    }

    @Override
    public long getTransactionsCount(int userId, String symbol, String type) {
        if(type.equals("long")){
            return transactionRepository.getCount(userId, symbol);
        } else {
            return transactionShortSellRepository.getCount(userId, symbol);
        }
    }
}
