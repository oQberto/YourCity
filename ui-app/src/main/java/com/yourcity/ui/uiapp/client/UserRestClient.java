package com.yourcity.ui.uiapp.client;

import com.yourcity.ui.uiapp.model.UserRepresentation;

public interface UserRestClient {

    UserRepresentation getUserById(Long id);
}
