package ng.samuel.fxmono.simulator.stock.preview;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stock/preview")
public class PreviewController {

    private final PreviewService previewService;

    @GetMapping("/all/{option}")
    public String fetchPreviewList(@PathVariable String option) {

        return previewService.fetchPreviewList(option);
    }

    @GetMapping("/peers")
    public String fetchPeersList(@RequestParam String symbol) {

        return previewService.fetchPeersList(symbol);
    }
}
