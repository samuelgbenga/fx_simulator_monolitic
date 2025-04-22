package ng.samuel.fxmono.simulator.auth;

import jakarta.servlet.http.HttpServletRequest;
import ng.samuel.fxmono.simulator.auth.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {
    ResponseEntity<UserInfo> signIn(SignInRequest signInRequest);

    ResponseEntity<UserInfo> signUp(SignUpRequest signUpRequest);

    ResponseEntity<UserInfo> getUserInfo(String email, int id);

    ResponseEntity<Void> changePassword(ChangePasswordRequest request);

    String validateJWT(HttpServletRequest request);

    HttpHeaders refreshAccessToken(String email, int id);

    ResponseEntity<CheckAuthResponse> checkAuth();

    ResponseEntity<SignOutResponse> signOut();

    ResponseEntity<Void> generatePasswordResetLink(String email) throws IOException;

    ResponseEntity<ValidationTokenResponse> validatePasswordResetToken(String token);

    ResponseEntity<Void> resetPassword(SignUpRequest request);
}
