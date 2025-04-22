package ng.samuel.fxmono.simulator.portfolio;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionShortSell;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    //private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<Portfolio> getPortfolio(HttpServletRequest request) {
        int id = Integer.parseInt(request.getAttribute("id").toString());

        return ResponseEntity.ok().body(portfolioService.getPortfolio(id));
    }

    @PostMapping("/buy-sell")
    public ResponseEntity<Transaction> buyAndSell
            (HttpServletRequest request, @RequestBody TransactionRequest tr) {

        int id = Integer.parseInt(request.getAttribute("id").toString());

        Transaction newTransaction = portfolioService.buyAndSell(id, tr);

        return ResponseEntity.ok().body(newTransaction);
    }

    @PostMapping("/sell-short-buy-to-cover")
    public ResponseEntity<TransactionShortSell> sellShortAndBuyToCover
            (HttpServletRequest request, @RequestBody TransactionRequest tr) {

        int id = Integer.parseInt(request.getAttribute("id").toString());

        TransactionShortSell newTransaction = portfolioService.shortSellAndBuyToCover(id, tr);

        return ResponseEntity.ok().body(newTransaction);
    }
}
