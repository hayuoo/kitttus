package com.kitttus.auth.domain;

import java.util.Set;

public record UserAccount(String username, String passwordHash, Set<String> roles, boolean enabled) {
}
