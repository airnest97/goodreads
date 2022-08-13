package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.models.MailResponse;
import africa.semicolon.goodreads.models.VerificationMessageRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service("mailgun_sender")
@NoArgsConstructor
@Slf4j
public class MailgunEmailService implements EmailService {
    private final String DOMAIN = System.getenv("DOMAIN");
    private final String PRIVATE_KEY = System.getenv("MAILGUN_PRIVATE_KEY");


    @Override
    @Async
    public CompletableFuture<MailResponse> sendHtmlMail(VerificationMessageRequest verificationMessageRequest) throws UnirestException {
        log.info("DOMAIN -> {}", DOMAIN);
        log.info("API KEY -> {}", PRIVATE_KEY);
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", PRIVATE_KEY)
                .queryString("from", verificationMessageRequest.getSender())
                .queryString("to", verificationMessageRequest.getReceiver())
                .queryString("subject", verificationMessageRequest.getSubject())
                .queryString("html", verificationMessageRequest.getBody())
                .asJson();
        MailResponse mailResponse = request.getStatus() == 200 ? new MailResponse(true) : new MailResponse(false);
        return CompletableFuture.completedFuture(mailResponse);
    }

    @Override
    public CompletableFuture<MailResponse> sendSimpleMail(VerificationMessageRequest verificationMessageRequest) throws UnirestException {
        log.info("DOMAIN -> {}", DOMAIN);
        log.info("API KEY -> {}", PRIVATE_KEY);
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", PRIVATE_KEY)
                .queryString("from", verificationMessageRequest.getSender())
                .queryString("to", verificationMessageRequest.getReceiver())
                .queryString("subject", verificationMessageRequest.getSubject())
                .queryString("text", verificationMessageRequest.getBody())
                .asJson();
        MailResponse mailResponse = request.getStatus() == 200 ? new MailResponse(true) : new MailResponse(false);
        return CompletableFuture.completedFuture(mailResponse);
    }
}
