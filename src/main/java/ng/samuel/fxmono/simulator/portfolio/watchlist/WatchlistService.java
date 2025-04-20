package ng.samuel.fxmono.simulator.portfolio.watchlist;

import java.util.HashMap;
import java.util.List;

public interface WatchlistService {
    HashMap<String, String> getWatchlist(int userId);

    void addToList(int userId, String symbol, String exchange);

    void removeFromList(int userId, String symbol);

    void removeFromListBatch(int userId, String[] symbols);

    List<Watchlist> findByPageNum(int userId, int pageNum);

    String findByPageNumWithPrice(int userId, int pageNum);
}
