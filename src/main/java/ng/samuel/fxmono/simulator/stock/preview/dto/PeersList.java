package ng.samuel.fxmono.simulator.stock.preview.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeersList {
    private String symbol;
    private List<String> peersList;

}
