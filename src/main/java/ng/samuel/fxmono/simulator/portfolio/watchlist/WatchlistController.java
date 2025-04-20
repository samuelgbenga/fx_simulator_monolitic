package ng.samuel.fxmono.simulator.portfolio.watchlist;


import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ng.samuel.fxmono.simulator.portfolio.dto.AddToWatchlistRequest;
import ng.samuel.fxmono.simulator.portfolio.dto.RemoveFromListRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @GetMapping("")
    public ResponseEntity<HashMap<String, String>> getWatchlist(HttpServletRequest request) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        HashMap<String, String> list = watchlistService.getWatchlist(id);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/by-page")
    public ResponseEntity<List<Watchlist>> getWatchlistByPage
            (HttpServletRequest request, @RequestParam int pageNum) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        List<Watchlist> list = watchlistService.findByPageNum(id, pageNum);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/by-page-with-price")
    public String getWatchlistByPage_withPrice
            (HttpServletRequest request, @RequestParam int pageNum) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        return watchlistService.findByPageNumWithPrice(id, pageNum);
    }

    @PostMapping("")
    public ResponseEntity<Void> addToWatchlist
            (HttpServletRequest request, @RequestBody AddToWatchlistRequest body) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        watchlistService.addToList(id, body.getSymbol(), body.getExchange());

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> removeFromWatchlist
            (HttpServletRequest request, @RequestParam String symbol) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        watchlistService.removeFromList(id, symbol);

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> removeFromWatchlist_batch
            (HttpServletRequest request, @RequestBody RemoveFromListRequest body) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        watchlistService.removeFromListBatch(id, body.getSymbols());

        return ResponseEntity.ok().body(null);
    }
}
