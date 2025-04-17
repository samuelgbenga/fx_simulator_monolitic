package ng.samuel.fxmono.simulator.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFromListRequest {
    private String[] symbols;
}