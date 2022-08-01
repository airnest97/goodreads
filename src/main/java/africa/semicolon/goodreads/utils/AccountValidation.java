package africa.semicolon.goodreads.utils;

import africa.semicolon.goodreads.dtos.AccountCreationRequest;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.repositories.UserRepository;
import africa.semicolon.goodreads.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountValidation {
    private static UserRepository userRepository;

    public AccountValidation(UserRepository userRepository) {
        AccountValidation.userRepository = userRepository;
    }

    public static void validate(AccountCreationRequest accountCreationRequest){
        User user = userRepository.findUserByEmail(accountCreationRequest.getEmail()).orElse(null);
        if (user != null){
            throw new GoodReadsException("User email already exist");
        }
    }
}
