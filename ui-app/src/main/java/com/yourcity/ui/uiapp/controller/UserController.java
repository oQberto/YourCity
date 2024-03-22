package com.yourcity.ui.uiapp.controller;

import com.yourcity.ui.uiapp.client.UserRestClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserController {
    UserRestClient userRestClient;

    @GetMapping("/{userId}")
    public String getUserNyId(Model model,
                              @PathVariable Long userId) {
        model.addAttribute("user", userRestClient.getUserById(userId));
        return "user/main";
    }
}
