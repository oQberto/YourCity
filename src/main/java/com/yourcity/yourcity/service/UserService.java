package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserRepresentationDto createUser(UserCreationDto dto);

    UserRepresentationDto getUserById(Long id);

    UserRepresentationDto updateUser(UserEditDto dto);

    Long deleteUser(Long id);
}
