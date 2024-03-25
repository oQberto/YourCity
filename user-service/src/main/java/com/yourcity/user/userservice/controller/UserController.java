package com.yourcity.user.userservice.controller;

import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.user.userservice.model.dto.UserCreationDto;
import com.yourcity.user.userservice.model.dto.UserEditDto;
import com.yourcity.user.userservice.model.dto.UserRepresentationDto;
import com.yourcity.user.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    MessageSource messageSource;

    @GetMapping(GET_USER_BY_ID)
    public ResponseEntity<UserRepresentationDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                Optional.of(userService.getUserById(userId))
                        .orElseThrow(() -> new NoSuchElementException("errors.user.not_found"))
        );
    }

    @GetMapping(GET_USERS_BY_NETWORK_STATUS)
    public ResponseEntity<List<UserRepresentationDto>> getUsersByNetworkStatus(@PathVariable NetworkStatus networkStatus,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(
                userService.getUsersByNetworkStatus(networkStatus, pageable)
        );
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationDto dto,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            UserRepresentationDto createdUser = userService.createUser(dto);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .pathSegment("{userId}")
                            .build(Map.of("userId", createdUser.getId())))
                    .body(createdUser);
        }
    }

    @PatchMapping(UPDATE_USER)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserEditDto dto,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            userService.updateUser(dto);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @DeleteMapping(DELETE_USER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> noSuchElementExceptionHAndler(NoSuchElementException exception,
                                                                       Locale locale) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(NOT_FOUND,
                        requireNonNull(messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)))
                );
    }
}
