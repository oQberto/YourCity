package com.yourcity.user.userservice.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreationDto {
    String username;
    String email;
    String password;
}
