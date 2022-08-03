package africa.semicolon.goodreads.controllers;

import africa.semicolon.goodreads.controllers.requestsAndResponse.ApiResponse;
import africa.semicolon.goodreads.controllers.requestsAndResponse.AccountCreationRequest;
import africa.semicolon.goodreads.controllers.requestsAndResponse.UpdateRequest;
import africa.semicolon.goodreads.dtos.UserDto;
import africa.semicolon.goodreads.exception.GoodReadsException;
import africa.semicolon.goodreads.models.User;
import africa.semicolon.goodreads.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody @Valid @NotNull AccountCreationRequest accountCreationRequest) throws GoodReadsException {
        try {
            log.info("Account creation request ==> {}", accountCreationRequest);
            UserDto userDto = userServices.createUserAccount(accountCreationRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("user created successfully")
                    .data(userDto)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (GoodReadsException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fails")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") @Valid @NotNull @NotBlank String userId) {
        try {
            if ("null".equals(userId) || ("").equals(userId.trim())) {
                throw new GoodReadsException("Sting id cannot be null", 404);
            }
            UserDto userDto = userServices.findUserById(userId);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("user found")
                    .data(userDto)
                    .result(1)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (GoodReadsException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fails")
                    .message(e.getMessage())
                    .result(0)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userServices.findAll();
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message(users.size() != 0 ? "users found" : "No user exists in database")
                .data(users)
                .result(users.size())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/")
    public ResponseEntity<?> updateUserProfile(@Valid @NotNull @NotBlank @RequestParam String id,
                                               @NotNull @RequestBody UpdateRequest updateRequest) {
        try {
            UserDto update = userServices.updateUserProfile(id, updateRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("user found")
                    .data(update)
                    .result(1)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (GoodReadsException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fails")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getStatusCode()));
        }

    }
}
