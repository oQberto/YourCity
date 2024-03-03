package com.yourcity.yourcity.controller.api;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    public static final String GET_USER_BY_ID = "/api/v1/users/{userId}";
    public static final String GET_USERS_BY_NETWORK_STATUS = "/api/v1/users/status/{networkStatus}";
    public static final String CREATE_USER = "/api/v1/users";
    public static final String UPDATE_USER = "/api/v1/users/user/update";
    public static final String DELETE_USER = "/api/v1/users/{userId}";

    UserService userService;

    @GetMapping(GET_USER_BY_ID)
    public ResponseEntity<UserRepresentationDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                userService.getUserById(userId)
        );
    }

    @GetMapping(GET_USERS_BY_NETWORK_STATUS)
    public ResponseEntity<List<UserRepresentationDto>> getUsersByNetworkStatus
            (@PathVariable NetworkStatus networkStatus) {
        return ResponseEntity.ok(
                userService.getUsersByNetworkStatus(networkStatus)
        );
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<UserRepresentationDto> createUser(UserCreationDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(dto));
    }

    @PatchMapping(UPDATE_USER)
    public ResponseEntity<UserRepresentationDto> updateUser(UserEditDto dto) {
        return ResponseEntity.ok(
                userService.updateUser(dto)
        );
    }

    @DeleteMapping(DELETE_USER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
