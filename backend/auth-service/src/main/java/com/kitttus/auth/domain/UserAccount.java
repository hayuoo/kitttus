package com.kitttus.auth.domain;

import java.util.Set;

public record UserAccount(
        String username,
        String passwordHash,
        Set<String> roles,
        Set<String> permissions,
        Set<String> tenantIds,
        boolean enabled) {
}
