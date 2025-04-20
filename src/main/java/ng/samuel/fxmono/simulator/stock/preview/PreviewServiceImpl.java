package ng.samuel.fxmono.simulator.stock.preview;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.stock.preview.dto.PeersList;
import ng.samuel.fxmono.simulator.utils.EnvVariable;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class PreviewServiceImpl implements PreviewService{

    private final WebClient webClient;
    private final String FMP_API_KEY;

    @Autowired
    public PreviewServiceImpl(WebClient.Builder webClientBuilder, EnvVariable env) {
        String FMP_API_URL = env.FMP_API_URL();
        this.FMP_API_KEY = env.FMP_API_KEY();

        webClient = webClientBuilder
                .baseUrl(FMP_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String fetchPreviewList(String option) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/" + option)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String fetchPeersList(String symbol) {
        PeersList[] list = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stable/stock-peers")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                // ----- (1) ----- //
                .bodyToMono(PeersList[].class)
                .block();

        return this.fetchPeersInfo(StringUtils.join(list[0].getPeersList(), ','));
    }


    private String fetchPeersInfo(String symbolList) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/quote/" + symbolList)
                        .queryParam("apikey", FMP_API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
