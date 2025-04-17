package ng.samuel.fxmono.simulator.portfolio.account;

import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.exception.exceptions.BadRequestException;
import ng.samuel.fxmono.simulator.exception.exceptions.InvalidTokenException;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionRequest;
import ng.samuel.fxmono.simulator.portfolio.dto.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository userRepository;

    @Override
//	@Transactional    // JPA-data includes the @Transactional, we don't need to add it anymore
    public List<Account> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Account findById(int id) {
        Optional<Account> result = userRepository.findById(id);

        return result.orElse(null);
    }

    @Override
    public Account findByEmail(String email) {

        Optional<Account> account = userRepository.findByEmail(email);
        return account.orElse(null);
    }

    @Override
    public void save(Account user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public void updateAccountFund
            (int id, double currentPrice, TransactionRequest tr, double realizedGainLoss) {
        Account account = findById(id);
        if(account == null) {
            throw new InvalidTokenException("Invalid ID found in the token");
        }

        double orderAmount = currentPrice * tr.getShares();
        switch (TransactionType.valueOf(tr.getType())) {
            case BUY: {
                account.setFund(account.getFund() - orderAmount);
                break;
            }
            case SELL: {
                account.setFund(account.getFund() + orderAmount);
                break;
            }
            case SELL_SHORT: break;

            case BUY_TO_COVER: {
                // since "sell short" does not modify the fund, the realizedGainLoss of "buy to cover"
                // will the exact amount that increase/decrease the fund
                account.setFund(account.getFund() + realizedGainLoss);
                break;
            }
            default: throw new BadRequestException("Invalid transaction type", "transaction");
        }

        save(account);
    }
}
