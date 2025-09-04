package ait.cohor63.shop.security;

import ait.cohor63.shop.security.service.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Bugaenko
 * {@code @date} 04.09.2025
 */

@Service
public class AuthService {

    private final UserDetailsService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage;

    public AuthService(UserDetailsService userService, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.refreshStorage = new HashMap<>();
    }
}
