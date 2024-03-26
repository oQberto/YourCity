package com.yourcity.ui.uiapp.client;

import com.yourcity.ui.uiapp.model.enums.NetworkStatus;
import com.yourcity.ui.uiapp.model.user.UserCreationDto;
import com.yourcity.ui.uiapp.model.user.UserEditDto;
import com.yourcity.ui.uiapp.model.user.UserRepresentationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRestClient {

    Optional<UserRepresentationDto> getUserById(Long id);

    List<UserRepresentationDto> getAllUsers(int page, int size);

    List<UserRepresentationDto> getUsersByNetworkStatus(NetworkStatus networkStatus, int page, int size);

    UserRepresentationDto createUser(UserCreationDto dto);

    UserRepresentationDto updateUser(UserEditDto dto);

    ResponseEntity<Void> deleteUser(Long id);
}
