package com.yourcity.yourcity.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreationDto {
    String username;
    String email;
    String password;
}
