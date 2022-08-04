package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
import africa.semicolon.goodreads.controllers.requestsAndResponse.UpdateRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.events.SendMessageEvent;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.MessageRequest;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserServices {
    private UserRepository userRepository;
    private ApplicationEventPublisher applicationEventPublisher;
    private ModelMapper modelMapper;
    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           EmailService emailService,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.modelMapper = mapper;
        this.emailService = emailService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public UserDto createUserAccount(AccountCreationRequest accountCreationRequest) throws GoodReadsException, UnirestException {
        validate(accountCreationRequest, userRepository);

        User user = User.builder()
                .firstName(accountCreationRequest.getFirstName())
                .lastName(accountCreationRequest.getLastName())
                .email(accountCreationRequest.getEmail())
                .password(accountCreationRequest.getPassword())
                .dateJoined(LocalDate.now())
                .build();

        MessageRequest message = MessageRequest.builder()
                .subject("VERIFY EMAIL")
                .sender("ehizman.tutoredafrica@gmail.com")
                .receiver(user.getEmail())
                .usersFullName(String.format("%s %s", user.getFirstName(), user.getLastName()))
                .build();

        SendMessageEvent event = new SendMessageEvent(message);
        applicationEventPublisher.publishEvent(event);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto findUserById(String userId) throws GoodReadsException {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(
                        ()-> new GoodReadsException(String.format("User with id %s not found" ,userId), 404));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map((user -> modelMapper.map(user, UserDto.class)))
                .toList();
    }

    @Override
    public UserDto updateUserProfile(String id, UpdateRequest updateRequest) throws GoodReadsException {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new GoodReadsException("User id not found", 404)
        );
        User savedUsed = modelMapper.map(updateRequest, User.class);
        savedUsed.setId(user.getId());
        savedUsed.setDateJoined(user.getDateJoined());
        userRepository.save(savedUsed);
        return modelMapper.map(savedUsed, UserDto.class);
    }

    private static void validate(AccountCreationRequest accountCreationRequest, UserRepository userRepository) throws GoodReadsException {
        log.info("validate");

        User user = userRepository.findUserByEmail(accountCreationRequest.getEmail()).orElse(null);
        if (user != null){
            throw new GoodReadsException("User email already exist", 400);
        }
    }
}
