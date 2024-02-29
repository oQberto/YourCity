package com.yourcity.yourcity.dto.user;

import com.yourcity.yourcity.model.entity.enums.NetworkStatus;
import com.yourcity.yourcity.model.entity.enums.Role;
import com.yourcity.yourcity.model.entity.enums.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreationDto {
    Long id;
    String username;
    String email;
    String password;
    Status status;
    Role role;
    NetworkStatus networkStatus;
    Boolean isVerified;
}
