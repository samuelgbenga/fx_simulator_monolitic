package ng.samuel.fxmono.simulator.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ng.samuel.fxmono.simulator.portfolio.account.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private int id;
    private String email;
    private double fund;
    private long joinedAt;

    public UserInfo(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.fund = account.getFund();
        this.joinedAt = account.getJoinedAt();
    }
}
