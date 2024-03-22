package com.yourcity.ui.uiapp.model;

import com.yourcity.ui.uiapp.model.enums.Role;
import com.yourcity.ui.uiapp.model.enums.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRepresentation {
    Long id;
    String username;
    String email;
    Status status;
    Role role;
}
