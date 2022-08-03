package africa.semicolon.goodreads.controllers.requestsAndResponse;

import africa.semicolon.goodreads.models.enums.AccountStatus;
import africa.semicolon.goodreads.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;

    private LocalDateTime dob;

    private LocalDateTime dateJoined;

    private String location;

    private AccountStatus accountStatus;

    private Gender gender;
}
