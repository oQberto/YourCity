package com.yourcity.ui.uiapp.client.impl;

import com.yourcity.ui.uiapp.client.UserRestClient;
import com.yourcity.ui.uiapp.client.exception.BadRequestException;
import com.yourcity.ui.uiapp.model.enums.NetworkStatus;
import com.yourcity.ui.uiapp.model.user.UserCreationDto;
import com.yourcity.ui.uiapp.model.user.UserEditDto;
import com.yourcity.ui.uiapp.model.user.UserRepresentationDto;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserRestClientImpl implements UserRestClient {
    private static final ParameterizedTypeReference<List<UserRepresentationDto>> USERS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    RestClient restClient;

    @Override
    public Optional<UserRepresentationDto> getUserById(Long id) {
        try {
            return Optional.ofNullable(restClient.get()
                    .uri("/api/v1/users/{userId}", id)
                    .retrieve()
                    .body(UserRepresentationDto.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserRepresentationDto> getAllUsers(int page, int size) {
        return restClient.get()
                .uri("/api/v1/users?page=%d&size=%d".formatted(page, size))
                .retrieve()
                .body(USERS_TYPE_REFERENCE);
    }

    @Override
    public List<UserRepresentationDto> getUsersByNetworkStatus(NetworkStatus networkStatus, int page, int size) {
        return restClient.get()
                .uri("/api/v1/users/status/{networkStatus}?page=%d&size=%d".formatted(page, size), networkStatus)
                .retrieve()
                .body(USERS_TYPE_REFERENCE);
    }

    @Override
    public UserRepresentationDto createUser(UserCreationDto dto) {
        try {
            return restClient.post()
                    .uri("/api/v1/users/user/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve()
                    .body(UserRepresentationDto.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public UserRepresentationDto updateUser(UserEditDto dto) {
        try {
            return restClient.patch()
                    .uri("/api/v1/users/user/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve()
                    .body(UserRepresentationDto.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        try {
            return restClient.delete()
                    .uri("/api/v1/users/{userId}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
