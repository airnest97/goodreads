package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.dtos.AccountCreationRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import africa.semicolon.goodreads.utils.AccountValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
public class UserServiceImpl implements UserServices {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public UserDto createUserAccount(AccountCreationRequest accountCreationRequest) throws GoodReadsException {
        AccountValidation.validate(accountCreationRequest);

        User user = User.builder()
                .firstName(accountCreationRequest.getFirstName())
                .lastName(accountCreationRequest.getLastName())
                .email(accountCreationRequest.getEmail())
                .password(accountCreationRequest.getPassword())
                .build();

        User savedUsed = userRepository.save(user);
        return modelMapper.map(savedUsed, UserDto.class);
    }
}
