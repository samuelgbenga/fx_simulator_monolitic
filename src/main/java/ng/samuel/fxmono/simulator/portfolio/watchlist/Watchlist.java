package ng.samuel.fxmono.simulator.portfolio.watchlist;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "watchlist")
@IdClass(WatchlistPk.class)
@AllArgsConstructor
public class Watchlist {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "timestamp")
    private long timestamp;

}
