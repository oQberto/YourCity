package com.yourcity.user.userservice.model.dto;

import com.yourcity.domain.domain.model.enums.NetworkStatus;
import com.yourcity.domain.domain.model.enums.Role;
import com.yourcity.domain.domain.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserEditDto {

    @Positive
    Long id;

    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]")
    String username;

    @Size(min = 6)
    String password;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    String email;

    Role role;
    Status status;
    NetworkStatus networkStatus;
}
