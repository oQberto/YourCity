package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import com.yourcity.yourcity.model.entity.User;
import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserRepresentationDto createUser(UserCreationDto dto);

    UserRepresentationDto getUserById(Long id);

    UserRepresentationDto updateUser(UserEditDto dto);

    Long deleteUser(Long id);

    List<UserRepresentationDto> getUsersByNetworkStatus(NetworkStatus networkStatus);
}
