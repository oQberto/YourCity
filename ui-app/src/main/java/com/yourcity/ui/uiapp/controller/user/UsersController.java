package com.yourcity.ui.uiapp.controller.user;

import com.yourcity.ui.uiapp.client.UserRestClient;
import com.yourcity.ui.uiapp.client.exception.BadRequestException;
import com.yourcity.ui.uiapp.model.enums.NetworkStatus;
import com.yourcity.ui.uiapp.model.user.UserCreationDto;
import com.yourcity.ui.uiapp.model.user.UserRepresentationDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UsersController {
    UserRestClient userRestClient;

    @GetMapping("/main")
    public String getMainPage() {
        return "user/main";
    }

    @GetMapping("/list")
    public String getAllUsers(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        List<UserRepresentationDto> users = userRestClient.getAllUsers(page, size);
        model.addAttribute("users", users);

        return "user/users";
    }

    @GetMapping("/network_status/{networkStatus}")
    public String getUsersByNetworkStatus(Model model,
                                          @PathVariable NetworkStatus networkStatus,
                                          @RequestParam int size, @RequestParam int page) {

        List<UserRepresentationDto> usersByNetworkStatus = userRestClient.getUsersByNetworkStatus(networkStatus, page, size);
        model.addAttribute("usersByNetworkStatus", usersByNetworkStatus);

        return "user/users";
    }

    @GetMapping("/create")
    public String getUserCreationPage() {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(ModelMap model, UserCreationDto dto, HttpServletResponse response) {
        try {
            UserRepresentationDto createdUser = userRestClient.createUser(dto);
            return "redirect:/users/%d".formatted(createdUser.id());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAllAttributes(Map.of(
                    "errors", exception.getErrors(),
                    "creatingUser", dto
            ));
            return "user/create";
        }
    }
}
