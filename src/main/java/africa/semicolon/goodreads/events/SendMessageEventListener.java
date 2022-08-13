package africa.semicolon.goodreads.events;

import africa.semicolon.goodreads.models.MailResponse;
import africa.semicolon.goodreads.models.VerificationMessageRequest;
import africa.semicolon.goodreads.services.EmailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class SendMessageEventListener {
    @Qualifier("mailgun_sender")
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private Environment env;

    @EventListener
    public void handleSendMessageEvent(SendMessageEvent event) throws UnirestException, ExecutionException, InterruptedException {
        VerificationMessageRequest verificationMessageRequest = (VerificationMessageRequest) event.getSource();

        String verificationLink = verificationMessageRequest.getDomainUrl()+"api/v1/auth/verify/"+ verificationMessageRequest.getVerificationToken();

        log.info("Message request --> {}", verificationMessageRequest);
        Context context = new Context();
        context.setVariable("user_name", verificationMessageRequest.getUsersFullName());
        context.setVariable("verification_token", verificationLink);
        if (Arrays.asList(env.getActiveProfiles()).contains("prod")){
            log.info("Message Event -> {}", event.getSource());
            verificationMessageRequest.setBody(templateEngine.process("registration_verification_mail.html", context));
            MailResponse mailResponse = emailService.sendSimpleMail((VerificationMessageRequest) event.getSource()).get();
            log.info("Mail Response --> {}", mailResponse);
        } else {
            verificationMessageRequest.setBody("https://google.com");
            MailResponse mailResponse = emailService.sendSimpleMail((VerificationMessageRequest) event.getSource()).get();
            log.info("Mail Response --> {}", mailResponse);
        }
    }
}
