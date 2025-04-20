package ng.samuel.fxmono.simulator.portfolio.asset;

import ng.samuel.fxmono.simulator.portfolio.dto.TransactionGainLoss;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;

import java.util.List;

public interface AssetService {
    List<Asset> findAllAssets(int userId);

    Asset findAssetBySymbol(int userId, String symbol);

    void saveAsset(Asset asset);

    TransactionGainLoss updateAsset(int userId, double currentPrice, TransactionRequest tr);
}
