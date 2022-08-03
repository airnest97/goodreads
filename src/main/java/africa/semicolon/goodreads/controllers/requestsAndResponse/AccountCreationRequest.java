package africa.semicolon.goodreads.controllers.requestsAndResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationRequest {
//    @JsonProperty("first_name")
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "AccountCreationRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
