package ng.samuel.fxmono.simulator.portfolio.asset;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "asset")
@IdClass(AssetPk.class)
public class Asset {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "avg_cost")
    private double avgCost;

    @Column(name = "shares")
    private int shares;

    @Column(name = "avg_borrowed")
    private double avgBorrowed;

    @Column(name = "shares_borrowed")
    private int sharesBorrowed;

    @Column(name = "realized_gain_loss")
    private double realizedGainLoss;

    @Column(name = "realized_gain_loss_short_selling")
    private double realizedGainLossShortSelling;

    @Column(name = "timestamp")
    private long timestamp;

    // anything marked with transient will not be persisted
    @Transient
    private double unrealizedGainLoss;

    @Transient
    private double unrealizedGainLossBorrowed;

    @Transient
    private double currentPrice;


    // create a new asset by passing userId and symbol
    public Asset(int userId, String symbol, String exchange) {
        this.userId = userId;
        this.symbol = symbol;
        this.exchange = exchange;
        this.avgCost = 0.0;
        this.shares = 0;
        this.avgBorrowed = 0.0;
        this.sharesBorrowed = 0;
        this.realizedGainLoss = 0;
        this.realizedGainLossShortSelling = 0;
        this.timestamp = System.currentTimeMillis();
    }
}
