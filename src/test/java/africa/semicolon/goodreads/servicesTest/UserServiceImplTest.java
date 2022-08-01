package africa.semicolon.goodreads.servicesTest;

import africa.semicolon.goodreads.dtos.AccountCreationRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import africa.semicolon.goodreads.services.UserServiceImpl;
import africa.semicolon.goodreads.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userServices = new UserServiceImpl(userRepository);
    }

    @Test
    void userCanCreateAccountTest(){
        AccountCreationRequest accountCreationRequest =
                new AccountCreationRequest("Ernest", "Ehigiator", "ernest@example.com", "password");
        UserDto userDto = userServices.createUserAccount(accountCreationRequest);

        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        assertThat(optionalUser.isPresent()).isEqualTo(true);
        assertThat(optionalUser.get().getFirstName()).isEqualTo("Ernest");
        assertThat(optionalUser.get().getLastName()).isEqualTo("Ehigiator");
        assertThat(optionalUser.get().getEmail()).isEqualTo("ernest@example.com");
        assertThat(optionalUser.get().getPassword()).isEqualTo("password");
    }

    @Test
    void userEmailIsUniqueTest() throws GoodReadsException{
        AccountCreationRequest accountCreationRequest1 =
                new AccountCreationRequest("Ernest", "Ehigiator", "ernest@example.com", "password");
        userServices.createUserAccount(accountCreationRequest1);

        AccountCreationRequest accountCreationRequest2 =
                new AccountCreationRequest("John", "Walker", "ernest@example.com", "key");

        assertThrows(GoodReadsException.class, () -> userServices.createUserAccount(accountCreationRequest2));
    }
}