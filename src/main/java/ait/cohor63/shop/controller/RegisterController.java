package ait.cohor63.shop.controller;

import ait.cohor63.shop.exception_handling.Response;
import ait.cohor63.shop.model.dto.UserRegisterDTO;
import ait.cohor63.shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private Response register (@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return new Response("Registration complete. Please check your e-mail");
    }
}

// http://localhost:8080/api/confirm?code=5b564d6d-05c0-4705-bc3f-6a938d782ded