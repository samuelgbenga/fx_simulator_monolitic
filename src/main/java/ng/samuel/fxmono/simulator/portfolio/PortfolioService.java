package ng.samuel.fxmono.simulator.portfolio;


import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;

public interface PortfolioService {
    Portfolio getPortfolio(int user_id);

    Transaction buyAndSell(int userId, TransactionRequest tr);

    TransactionShortSell shortSellAndBuyToCover(int userId, TransactionRequest tr);
}
