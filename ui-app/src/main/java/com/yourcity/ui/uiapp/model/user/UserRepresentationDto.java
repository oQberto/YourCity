package com.yourcity.ui.uiapp.model.user;

import com.yourcity.ui.uiapp.model.enums.Role;
import com.yourcity.ui.uiapp.model.enums.Status;

public record UserRepresentationDto(
        Long id,
        String username,
        String email,
        Status status,
        Role role) {

}
