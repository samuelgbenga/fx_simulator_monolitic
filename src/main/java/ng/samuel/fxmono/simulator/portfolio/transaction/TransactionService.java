package ng.samuel.fxmono.simulator.portfolio.transaction;

import ng.samuel.fxmono.simulator.portfolio.dto.TransactionGainLoss;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionTemplate;

import java.util.List;

public interface TransactionService {

    List<Transaction> findByUserIdAndSymbol (int userId, String symbol);

    void saveTransaction(Transaction transaction);

    void saveTransactionShortSell(TransactionShortSell transactionSS);

    Transaction addNewTransaction
            (int userId, double currentPrice,
             TransactionRequest tr, TransactionGainLoss transactionGainLoss);

    TransactionShortSell addNewTransactionShortSell
            (int userId, double currentPrice,
             TransactionRequest tr, TransactionGainLoss transactionGainLoss);

    List<? extends TransactionTemplate> getTransactionsByPage(int userId, String symbol, int pageNum, String type);

    long getTransactionsCount(int userId, String symbol, String type);
}
