package africa.semicolon.goodreads.models;

import lombok.*;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageRequest {
    @Email
    private String sender;
    @Email
    private String receiver;
    private String body;
    private String subject;
    private String usersFullName;

}
