package ng.samuel.fxmono.simulator.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddToWatchlistRequest {
    private String symbol;
    private String exchange;
}