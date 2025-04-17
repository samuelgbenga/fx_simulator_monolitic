package ng.samuel.fxmono.simulator.utils;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class EnvVariable {

    private final Dotenv env = Dotenv.load();

    public String FMP_API_KEY() {
        return env.get("FMP_API_KEY");
    }

    public String FMP_API_URL() {
        return env.get("FMP_API_URL");
    }

    public String TOKEN_SECRET() { return env.get("JWT_SECRET"); }

    public String SECURITY_CIPHER_KEY() { return env.get("SECURITY_CIPHER_KEY"); }

}
