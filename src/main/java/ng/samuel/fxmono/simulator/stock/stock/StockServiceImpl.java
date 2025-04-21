package ng.samuel.fxmono.simulator.stock.stock;

import ng.samuel.fxmono.simulator.stock.stock.dto.SearchResult;
import ng.samuel.fxmono.simulator.stock.stock.dto.StockNews;
import ng.samuel.fxmono.simulator.utils.EnvVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

public class StockServiceImpl implements StockService{

    private final WebClient webClient;
    private final String FMP_API_KEY;

    @Autowired
    public StockServiceImpl(WebClient.Builder webClientBuilder, EnvVariable env) {
        String FMP_API_URL = env.FMP_API_URL();
        this.FMP_API_KEY = env.FMP_API_KEY();

        // increase the WebClient default "maxInMemorySize"
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        webClient = webClientBuilder
                .baseUrl(FMP_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                // increase the WebClient default "maxInMemorySize"
                .exchangeStrategies(strategies)
                .build();
    }

    @Override
    public StockNews[] fetchNews(String symbol, String limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/fmp-articles")
                        .queryParam("page", 0)
                        .queryParam("limit", limit)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(StockNews[].class)
                .block();
    }

    @Override
    public SearchResult[] searchStock(String query) {
        String exchanges = "NASDAQ,NYSE";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/search-name")
                        .queryParam("query", query)
                        .queryParam("exchange", exchanges)
                        .queryParam("limit", 20)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(SearchResult[].class)
                .block();
    }

    @Override
    public String fetchFinancialStatement(String symbol,  String period, int limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/cash-flow-statement")
                        .queryParam("symbol", symbol)
                        .queryParam("period", period)
                        .queryParam("limit", limit)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String fetchCompanyProfile(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/profile/")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
