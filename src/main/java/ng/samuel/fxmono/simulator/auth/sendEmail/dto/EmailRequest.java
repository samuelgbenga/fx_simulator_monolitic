package ng.samuel.fxmono.simulator.auth.sendEmail.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String fullName;


    private String subject;

    private String event;


    private String recipient;
}
