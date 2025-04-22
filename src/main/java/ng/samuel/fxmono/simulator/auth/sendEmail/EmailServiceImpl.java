package ng.samuel.fxmono.simulator.auth.sendEmail;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import ng.samuel.fxmono.simulator.auth.sendEmail.dto.EmailRequest;
import ng.samuel.fxmono.simulator.utils.EnvVariable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final EnvVariable envVariable;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine tEngine;

    @Override
    public void sendEmail(EmailRequest emailDetails) throws MessagingException{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", emailDetails.getEvent(),
                "event", emailDetails.getEvent()
        );
        context.setVariables(variables);
        helper.setFrom(envVariable.EMAIL_NAME());
        helper.setTo(emailDetails.getRecipient());
        helper.setSubject(emailDetails.getSubject());
        String html = tEngine.process("email", context);
        helper.setText(html, true);

        // Add an attachment
//        File attachment = new File("/path/to/your/attachment.pdf"); // Specify the file path
//        if (attachment.exists()) {
//            helper.addAttachment("attachment.pdf", attachment); // Name the attachment as it should appear in the email
//        }

        // Load the file from the resources folder
//        Resource resource = new ClassPathResource("static/xmas.jpg"); // Path relative to src/main/resources
//        if (resource.exists()) {
//            File file = resource.getFile();
//            helper.addAttachment("xmas.jpg", file); // Name the attachment as it should appear in the email
//        }


        javaMailSender.send(msg);
    }
}
