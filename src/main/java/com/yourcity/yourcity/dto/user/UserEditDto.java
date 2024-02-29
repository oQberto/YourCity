package com.yourcity.yourcity.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserEditDto {

    Long id;
    String username;
    String password;
    String email;
}
