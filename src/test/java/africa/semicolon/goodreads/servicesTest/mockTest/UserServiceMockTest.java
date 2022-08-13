//package africa.semicolon.goodreads.servicesTest.mockTest;
//
//import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
//import africa.semicolon.goodreads.dtos.UserDto;
//import africa.semicolon.goodreads.exception.GoodReadsException;
//import africa.semicolon.goodreads.models.User;
//import africa.semicolon.goodreads.repositories.UserRepository;
//import africa.semicolon.goodreads.services.EmailService;
//import africa.semicolon.goodreads.services.UserServiceImpl;
//import africa.semicolon.goodreads.services.UserServices;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceMockTest {
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private ModelMapper mapper;
//    private UserServices userServices;
//    @Mock
//    private EmailService emailService;
//    @Mock
//    private ApplicationEventPublisher applicationEventPublisher;
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
////    @Captor
////    private ArgumentCaptor<User> userArgumentCaptor;
//
//    @BeforeEach
//    void setUp() {
//        userServices = new UserServiceImpl(userRepository, mapper, emailService, applicationEventPublisher, bCryptPasswordEncoder);
//    }
//
//    @Test
//    void userCanCreateAccountTest() throws GoodReadsException, UnirestException {
//        AccountCreationRequest accountCreationRequest =
//                new AccountCreationRequest("Ernest", "Ehigiator", "ernest@example.com", "password");
//
//        User userToReturn = User.builder()
//                .id(1L)
//                .firstName(accountCreationRequest.getFirstName())
//                .lastName(accountCreationRequest.getLastName())
//                .email(accountCreationRequest.getEmail())
//                .password(accountCreationRequest.getPassword())
//                .build();
//
//        UserDto userDtoToReturn = UserDto.builder()
//                .id(1L)
//                .firstName(accountCreationRequest.getFirstName())
//                .lastName(accountCreationRequest.getLastName())
//                .email(accountCreationRequest.getEmail())
//                .build();
//
//        when(userRepository.findUserByEmail("ernest@example.com")).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(userToReturn);
//
//        when(mapper.map(userToReturn, UserDto.class)).thenReturn(userDtoToReturn);
//
//        UserDto userDto = userServices.createUserAccount(accountCreationRequest);
//
//        verify(userRepository, times(1)).findUserByEmail("ernest@example.com");
////        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
//
////        User capturedUser = userArgumentCaptor.getValue();
//
////        assertThat(capturedUser.getId()).isEqualTo(1L);
//
//
//        assertThat(userDto.getId()).isEqualTo(1L);
//        assertThat(userDto.getFirstName()).isEqualTo("Ernest");
//        assertThat(userDto.getLastName()).isEqualTo("Ehigiator");
//        assertThat(userDto.getEmail()).isEqualTo("ernest@example.com");
//
//    }
//
//    @Test
//    void userEmailIsUniqueTest() throws GoodReadsException, UnirestException {
//        AccountCreationRequest accountCreationRequest =
//                new AccountCreationRequest("Ernest", "Ehigiator", "ernest@example.com", "password");
//
//        User userToReturn = User.builder()
//                .id(1L)
//                .firstName(accountCreationRequest.getFirstName())
//                .lastName(accountCreationRequest.getLastName())
//                .email(accountCreationRequest.getEmail())
//                .password(accountCreationRequest.getPassword())
//                .build();
//
//        when(userRepository.findUserByEmail("ernest@example.com")).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(userToReturn);
//        userServices.createUserAccount(accountCreationRequest);
//
//        verify(userRepository, times(1)).findUserByEmail("ernest@example.com");
//
//        AccountCreationRequest accountCreationRequest1 =
//                new AccountCreationRequest("James", "Bond", "ernest@example.com", "password");
//
//        when(userRepository.findUserByEmail("ernest@example.com")).thenReturn(Optional.of(userToReturn));
//        assertThatThrownBy(() -> userServices.createUserAccount(accountCreationRequest1))
//                .isInstanceOf(GoodReadsException.class)
//                .hasMessage("User email already exist");
//        verify(userRepository, times(2)).findUserByEmail("ernest@example.com");
//    }
//
//    @Test
//    void userCanBeFoundByIdTest() throws GoodReadsException {
//        String userId = "1";
//
//        User user = User.builder()
//                .id(1L)
//                .firstName("Men")
//                .lastName("woman")
//                .email("mail@mail.com")
//                .password("1234")
//                .build();
//
//        UserDto userDtoToReturn = UserDto.builder()
//                .id(1L)
//                .firstName("Men")
//                .lastName("woman")
//                .email("mail@mail.com")
//                .build();
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        when(mapper.map(user, UserDto.class)).thenReturn(userDtoToReturn);
//
//        UserDto userDto = userServices.findUserById(userId);
//
//        verify(userRepository, times(1)).findById(1L);
//
//        assertThat(userDto.getId()).isEqualTo(1L);
//        assertThat(userDto.getFirstName()).isEqualTo("Men");
//        assertThat(userDto.getLastName()).isEqualTo("woman");
//        assertThat(userDto.getEmail()).isEqualTo("mail@mail.com");
//    }
//}
