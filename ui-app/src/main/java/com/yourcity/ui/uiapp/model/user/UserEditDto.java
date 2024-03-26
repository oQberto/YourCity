package com.yourcity.ui.uiapp.model.user;

import com.yourcity.ui.uiapp.model.enums.Role;
import com.yourcity.ui.uiapp.model.enums.Status;

public record UserEditDto(
        Long id,
        String username,
        String password,
        String email,
        Role role,
        Status status) {
}
