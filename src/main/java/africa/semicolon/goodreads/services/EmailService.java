package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.models.MailResponse;
import africa.semicolon.goodreads.models.MessageRequest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<MailResponse> sendSimpleMail(MessageRequest messageRequest) throws UnirestException;

    void sendHtmlMail(MessageRequest messageRequest);
}
