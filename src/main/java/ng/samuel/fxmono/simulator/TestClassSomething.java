package ng.samuel.fxmono.simulator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.utils.EnvVariable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestClassSomething {

    private final EnvVariable envVariable;

    @PostConstruct
    public void run() {
        System.out.println("FMP_API_KEY = " + envVariable.FMP_API_KEY());
        System.out.println("FMP_API_URL = " + envVariable.FMP_API_URL());
        System.out.println("FMP_API_URL = " + envVariable.TOKEN_SECRET());
        System.out.println("FMP_API_URL = " + envVariable.SECURITY_CIPHER_KEY());
    }
}
