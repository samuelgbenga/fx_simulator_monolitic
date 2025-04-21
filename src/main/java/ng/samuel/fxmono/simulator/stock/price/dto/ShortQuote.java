package ng.samuel.fxmono.simulator.stock.price.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortQuote {
    private String symbol;
    private double price;
    private long volume;
}
