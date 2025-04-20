package ng.samuel.fxmono.simulator.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import ng.samuel.fxmono.simulator.portfolio.asset.Asset;
import ng.samuel.fxmono.simulator.portfolio.dto.AccountResponse;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class Portfolio {
    private AccountResponse account;
    private List<String> symbols;
    private HashMap<String, Asset> assets;
    private HashMap<String, String> watchlist;
}
