package ng.samuel.fxmono.simulator.portfolio.asset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class AssetPk implements Serializable {

    private int userId;

    private String symbol;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetPk accountPK = (AssetPk) o;
        return userId == accountPK.userId &&
                symbol.equals(accountPK.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, symbol);
    }

}
