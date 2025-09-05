package ait.cohor63.shop.security.controller;

import ait.cohor63.shop.security.AuthService;
import ait.cohor63.shop.security.dto.LoginRequestDTO;
import ait.cohor63.shop.security.dto.RefreshRequestDTO;
import ait.cohor63.shop.security.dto.TokenResponseDTO;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Bugaenko
 * {@code @date} 05.09.2025
 */

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            return authService.login(loginRequestDTO);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshAccessToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {

        try {
            return authService.refreshAccessToken(refreshRequestDTO);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }



}
