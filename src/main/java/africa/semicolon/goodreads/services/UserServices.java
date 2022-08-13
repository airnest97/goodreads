package africa.semicolon.goodreads.services;

import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
import africa.semicolon.goodreads.controllers.requestsAndResponse.UpdateRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public interface UserServices {
    UserDto createUserAccount(String host, AccountCreationRequest accountCreationRequest) throws GoodReadsException, UnirestException;

    UserDto findUserById(String userId) throws GoodReadsException;

    List<UserDto> findAll();

    UserDto updateUserProfile(String id, UpdateRequest updateRequest) throws GoodReadsException;
    User findUserByEmail(String email) throws GoodReadsException;
    void verifyUser(String token) throws GoodReadsException;
}
