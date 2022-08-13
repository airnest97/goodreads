package africa.semicolon.goodreads.models;

import lombok.*;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VerificationMessageRequest {
    @Email
    private String sender;
    @Email
    private String receiver;
    private String body;
    private String subject;
    private String usersFullName;
    private String verificationToken;
    private String domainUrl;
}
