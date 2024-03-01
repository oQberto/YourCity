package com.yourcity.yourcity.controller;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    public static final String GET_USER_BY_ID = "/api/v1/users/{userId}";
    public static final String CREATE_USER = "/api/v1/users/user/creation";
    public static final String UPDATE_USER = "/api/v1/users/user/update";
    public static final String DELETE_USER = "/api/v1/users/{userId}";

    @GetMapping(GET_USER_BY_ID)
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                userService.getUserById(userId)
        );
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<UserDto> createUser(UserCreationDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(dto));
    }

    @PatchMapping(UPDATE_USER)
    public ResponseEntity<UserDto> updateUser(UserEditDto dto) {
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
