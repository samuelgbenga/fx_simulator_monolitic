package ng.samuel.fxmono.simulator.stock.price.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory {
    private String date;
    private double open;
    private double low;
    private double high;
    private double close;
    private long volume;

}
