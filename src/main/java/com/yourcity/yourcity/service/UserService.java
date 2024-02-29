package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.user.UserCreationDto;
import com.yourcity.yourcity.dto.user.UserDto;
import com.yourcity.yourcity.dto.user.UserEditDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto createUser(UserCreationDto dto);

    UserDto getUserById(Long id);

    UserDto updateUser(UserEditDto dto);

    Long deleteUser(Long id);
}
