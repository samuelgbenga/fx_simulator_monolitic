package ng.samuel.fxmono.simulator.portfolio.transaction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction_short_selling")
public class TransactionShortSell extends TransactionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "short_sell")
    private boolean shortSell;

    @Column(name = "price_per_share")
    private double pricePerShare;

    @Column(name = "shares")
    private int shares;

    @Column(name = "realized_gain_loss")
    private double realizedGainLoss;

    @Column(name = "asset_total_realized_gain_loss")
    private double assetTotalRealizedGainLoss;

    @Column(name = "timestamp")
    private long timestamp;

}
