package ng.samuel.fxmono.simulator.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAuthResponse {
    private boolean hasAuth;
}
