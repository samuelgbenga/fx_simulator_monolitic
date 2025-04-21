package ng.samuel.fxmono.simulator.stock.price.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PriceHistoryList {

    private List<PriceHistory> historical;

    public PriceHistoryList(List<PriceHistory> historical) {
        this.historical = historical;
    }
}
