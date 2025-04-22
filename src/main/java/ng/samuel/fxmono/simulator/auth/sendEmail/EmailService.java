package ng.samuel.fxmono.simulator.auth.sendEmail;

import jakarta.mail.MessagingException;
import ng.samuel.fxmono.simulator.auth.sendEmail.dto.EmailRequest;

import java.io.IOException;

public interface EmailService {

    void sendEmail(EmailRequest emailRequest) throws MessagingException;
}
