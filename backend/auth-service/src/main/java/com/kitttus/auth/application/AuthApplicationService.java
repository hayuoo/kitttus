package com.kitttus.auth.application;

import com.kitttus.auth.api.dto.LoginRequest;
import com.kitttus.auth.api.dto.TokenResponse;
import com.kitttus.auth.infra.InMemoryUserStore;
import com.kitttus.security.jwt.JwtTokenProvider;
import java.util.Map;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {
    private final InMemoryUserStore userStore;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthApplicationService(InMemoryUserStore userStore, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(LoginRequest request) {
        var user = userStore.findByUsername(request.username())
                .filter(account -> account.enabled() && passwordEncoder.matches(request.password(), account.passwordHash()))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        var token = jwtTokenProvider.createToken(
                user.username(),
                user.roles(),
                Map.of("permissions", user.permissions(), "tenantIds", user.tenantIds()));

        return new TokenResponse(token, "Bearer", user.username(), user.roles(), user.permissions(), user.tenantIds());
    }
}
