package ng.samuel.fxmono.simulator.portfolio.transaction;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionCount;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.Transaction;
import ng.samuel.fxmono.simulator.portfolio.transaction.entity.TransactionTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request,
                                                             @RequestParam String symbol) {
        int id = Integer.parseInt(request.getAttribute("id").toString());
        List<Transaction> transactions = transactionService.findByUserIdAndSymbol(id, symbol);

        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/by-page")
    public ResponseEntity<List<? extends TransactionTemplate>> getTransactionsByTime
            (HttpServletRequest request, @RequestParam String symbol,
             @RequestParam int pageNum, @RequestParam String type) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        List<? extends TransactionTemplate> transactions =
                transactionService.getTransactionsByPage(id, symbol, pageNum, type);

        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/count")
    public ResponseEntity<TransactionCount> getTransactionsCount
            (HttpServletRequest request, @RequestParam String symbol, @RequestParam String type) {

        int id = Integer.parseInt(request.getAttribute("id").toString());
        long count = transactionService.getTransactionsCount(id, symbol, type);

        return ResponseEntity.ok().body(new TransactionCount(count));
    }
}