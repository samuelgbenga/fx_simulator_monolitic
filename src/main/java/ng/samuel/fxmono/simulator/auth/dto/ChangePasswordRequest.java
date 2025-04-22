package ng.samuel.fxmono.simulator.auth.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {

    private String email;

    private String password;
    @Size(min=8, max=20, message="Password must be between 8 and 20 characters in length")
    private String new_password;
    @Size(min=8, max=20, message="Password must be between 8 and 20 characters in length")
    private String confirm_password;

    public boolean passwordsMatched() {
        return new_password.equals(confirm_password);
    }
}
