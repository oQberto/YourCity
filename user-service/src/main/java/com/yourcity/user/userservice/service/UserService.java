package com.yourcity.user.userservice.service;

import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.user.userservice.model.dto.UserCreationDto;
import com.yourcity.user.userservice.model.dto.UserEditDto;
import com.yourcity.user.userservice.model.dto.UserRepresentationDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserRepresentationDto createUser(UserCreationDto dto);

    UserRepresentationDto getUserById(Long id);

    UserRepresentationDto updateUser(UserEditDto dto);

    Long deleteUser(Long id);

    List<UserRepresentationDto> getUsersByNetworkStatus(NetworkStatus networkStatus, Pageable pageable);
}
