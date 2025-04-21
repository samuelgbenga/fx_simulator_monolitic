package ng.samuel.fxmono.simulator.stock.price;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.stock.price.dto.PriceHistory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/historical-price")
    public String fetchHistoricalPrice(@RequestParam String from,
                                       @RequestParam String to,
                                       @RequestParam String symbol) {

        return priceService.fetchHistoricalPrice( from, to, symbol);
    }

    @GetMapping("/historical-price-full")
    public List<PriceHistory> fetchHistoricalPrice_full(@RequestParam String from,
                                                        @RequestParam String to,
                                                        @RequestParam String symbol,
                                                        @RequestParam String time_option) {

        return priceService.fetchHistoricalPrice_full(from, to, symbol, time_option);
    }

    @GetMapping("/real-time-quote")
    public String fetchRealTimeQuote(@RequestParam String symbol) {

        return priceService.fetchRealTimeQuote(symbol);
    }

    @GetMapping("/short-quote")
    public String fetchShortQuote(@RequestParam String symbol) {

        return priceService.fetchShortQuote(symbol);
    }

    @GetMapping("/financial-ratio")
    public String fetchFinancialRatios(@RequestParam String symbol) {

        return priceService.fetchFinancialRatios(symbol);
    }

    @GetMapping("/price-change")
    public String fetchPriceChange(@RequestParam String symbol) {

        return priceService.fetchPriceChange(symbol);
    }

}
