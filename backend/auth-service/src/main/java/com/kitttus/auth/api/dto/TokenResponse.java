package com.kitttus.auth.api.dto;

import java.util.Set;

public record TokenResponse(String accessToken, String tokenType, String username, Set<String> roles) {
}
