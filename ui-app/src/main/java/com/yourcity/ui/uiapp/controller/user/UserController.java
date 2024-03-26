package com.yourcity.ui.uiapp.controller.user;

import com.yourcity.ui.uiapp.client.UserRestClient;
import com.yourcity.ui.uiapp.client.exception.BadRequestException;
import com.yourcity.ui.uiapp.model.user.UserEditDto;
import com.yourcity.ui.uiapp.model.user.UserRepresentationDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/{userId:\\d+}")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserController {
    UserRestClient userRestClient;
    MessageSource messageSource;

    @ModelAttribute("user")
    public UserRepresentationDto getUser(@PathVariable("userId") Long id) {
        return userRestClient.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException("errors.user.not_found"));
    }

    @GetMapping
    public String getUserById() {
        return "user/user";
    }

    @GetMapping("/edit")
    public String getUSerEditPage() {
        return "user/edit";
    }

    @PostMapping("/edit")
    public String updateUser(Model model,
                             UserEditDto dto,
                             HttpServletResponse response) {
        try {
            userRestClient.updateUser(dto);
            return "redirect:/users/%d".formatted(dto.id());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAllAttributes(Map.of(
                    "errors", exception.getErrors(),
                    "updatingUser", dto
            ));
            return "user/edit";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
        userRestClient.deleteUser(id);
        return "redirect:/users/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               HttpServletResponse response,
                                               Locale locale,
                                               Model model) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                messageSource.getMessage(exception.getMessage(),
                        new Object[0], exception.getMessage(), locale)
        );
        return "error/4xx/404";
    }
}
