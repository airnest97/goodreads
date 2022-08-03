package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
import africa.semicolon.goodreads.controllers.requestsAndResponse.UpdateRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserServices {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.modelMapper = mapper;
    }

    @Override
    public UserDto createUserAccount(AccountCreationRequest accountCreationRequest) throws GoodReadsException {
        validate(accountCreationRequest, userRepository);

        log.info("after validation");

        User user = User.builder()
                .firstName(accountCreationRequest.getFirstName())
                .lastName(accountCreationRequest.getLastName())
                .email(accountCreationRequest.getEmail())
                .password(accountCreationRequest.getPassword())
                .build();

        User savedUsed = userRepository.save(user);
        return modelMapper.map(savedUsed, UserDto.class);
    }

    private static void validate(AccountCreationRequest accountCreationRequest, UserRepository userRepository) throws GoodReadsException {
        log.info("validate");

        User user = userRepository.findUserByEmail(accountCreationRequest.getEmail()).orElse(null);
        if (user != null){
            throw new GoodReadsException("User email already exist", 400);
        }
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
}
