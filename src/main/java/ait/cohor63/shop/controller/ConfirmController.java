package ait.cohor63.shop.controller;

import ait.cohor63.shop.exception_handling.Response;
import ait.cohor63.shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */

@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    private final UserService  userService;

    public ConfirmController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Response confirmEmail(@RequestParam String code) {
        return new Response(userService.confirmEmail(code));
    }
}
