package ng.samuel.fxmono.simulator.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieHelper {

    private final String accessToken = "accessToken";
    private final String refreshToken = "refreshToken";
    private final SecurityCipher securityCipher;

    public String createAccessTokenCookie(String token, long duration) {
        String encryptedToken = securityCipher.encrypt(token); // replace with your encryption logic

        return ResponseCookie.from(accessToken, encryptedToken)
                .maxAge(duration / 1000)  // convert from milliseconds to seconds
                .httpOnly(true)           // prevents JS access to the cookie
                .secure(false)            // disable for local dev (no HTTPS)
                .sameSite("Lax")          // "Strict", "Lax", or "None"
                .path("/")                // available to all paths
                .build()
                .toString();
    }

    public String createRefreshTokenCookie(String token, long duration) {
        String encryptedToken = securityCipher.encrypt(token);

        return ResponseCookie.from(refreshToken, encryptedToken)
                .maxAge(duration / 1000)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .build()
                .toString();
    }

    public String deleteAccessTokenCookie() {
        return ResponseCookie.from(accessToken, "deleted")
                .maxAge(0)                // expire the cookie immediately
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .build()
                .toString();
    }

    public String deleteRefreshTokenCookie() {
        return ResponseCookie.from(refreshToken, "deleted")
                .maxAge(0)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .build()
                .toString();
    }
}
