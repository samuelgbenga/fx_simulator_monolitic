package ng.samuel.fxmono.simulator.stock.stock.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    private String symbol;
    private String name;
    private String currency;
    private String stockExchange;
    private String exchangeShortName;
}
