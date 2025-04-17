package ng.samuel.fxmono.simulator.portfolio.transaction.entity;

public abstract class TransactionTemplate {
    int id;

    int userId;

    String symbol;

    double pricePerShare;

    int shares;

    double realizedGainLoss;

    double assetTotalRealizedGainLoss;

    long timestamp;
}