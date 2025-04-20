package ng.samuel.fxmono.simulator.portfolio.watchlist;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class WatchlistPk implements Serializable {

    private int userId;
    private String symbol;



    @Override
    public int hashCode() {
        return Objects.hash(userId, symbol);
    }
}
