package africa.semicolon.goodreads.servicesTest;

import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import africa.semicolon.goodreads.services.UserServiceImpl;
import africa.semicolon.goodreads.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
class UserServiceImplTest {
    private UserServices userServices;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userServices = new UserServiceImpl(userRepository, mapper);
    }

    @Test
    void userCanCreateAccountTest() throws GoodReadsException {
        AccountCreationRequest accountCreationRequest =
                new AccountCreationRequest("paul", "scoff", "spring@example.com", "password");
        UserDto userDto = userServices.createUserAccount(accountCreationRequest);

        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        assertThat(optionalUser.isPresent()).isEqualTo(true);
        assertThat(optionalUser.get().getFirstName()).isEqualTo("paul");
        assertThat(optionalUser.get().getLastName()).isEqualTo("scoff");
        assertThat(optionalUser.get().getEmail()).isEqualTo("spring@example.com");
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