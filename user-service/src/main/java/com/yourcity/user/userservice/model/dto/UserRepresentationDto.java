package com.yourcity.user.userservice.model.dto;

import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.domain.domain.model.enums.Role;
import com.yourcity.domain.domain.model.enums.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRepresentationDto {
    Long id;
    String username;
    String email;
    Status status;
    Role role;
    NetworkStatus networkStatus;
}
