package ng.samuel.fxmono.simulator.stock.stock;

import ng.samuel.fxmono.simulator.stock.stock.dto.SearchResult;
import ng.samuel.fxmono.simulator.stock.stock.dto.StockNews;

public interface StockService {

    StockNews[] fetchNews(String symbol, String limit);
    SearchResult[] searchStock(String query);
    String fetchFinancialStatement(String symbol, String period, int limit);
    String fetchCompanyProfile(String symbol);
}
