package ng.samuel.fxmono.simulator.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInRequest {

    @Email(message="Invalid email")
    @NotBlank(message="Required field")
    private String email;

    @Size(min=8, max=20, message="Password must be between 8 and 20 characters in length")
    private String password;
}
