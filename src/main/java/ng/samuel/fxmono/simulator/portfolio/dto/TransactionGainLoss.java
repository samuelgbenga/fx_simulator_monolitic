package ng.samuel.fxmono.simulator.portfolio.dto;

import lombok.Data;

@Data
public class TransactionGainLoss {
    private double realizedGainLoss;
    private double assetTotalRealizedGainLoss;
}