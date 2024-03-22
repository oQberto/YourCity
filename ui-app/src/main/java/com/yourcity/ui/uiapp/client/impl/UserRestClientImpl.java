package com.yourcity.ui.uiapp.client.impl;

import com.yourcity.ui.uiapp.client.UserRestClient;
import com.yourcity.ui.uiapp.model.UserRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.client.RestClient;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserRestClientImpl implements UserRestClient {
    RestClient restClient;

    @Override
    public UserRepresentation getUserById(Long id) {
        return restClient
                .get()
                .uri("/api/v1/users/{userId}", id)
                .retrieve()
                .body(UserRepresentation.class);
    }
}
