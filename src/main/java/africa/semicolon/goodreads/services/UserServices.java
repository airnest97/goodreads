package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.dtos.AccountCreationRequest;
import africa.semicolon.goodreads.dtos.UserDto;

public interface UserServices {
    UserDto createUserAccount(AccountCreationRequest accountCreationRequest);
}
