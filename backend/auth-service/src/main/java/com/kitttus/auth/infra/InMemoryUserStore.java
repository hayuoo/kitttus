package com.kitttus.auth.infra;

import com.kitttus.auth.domain.UserAccount;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserStore {
    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public InMemoryUserStore(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void init() {
        users.put("organizer.admin", new UserAccount(
                "organizer.admin",
                passwordEncoder.encode("ChangeMe123!"),
                Set.of("ORGANIZER_ADMIN", "APPROVER", "USER"),
                Set.of("ORDER_CREATE", "CONTRACT_SIGN", "APPROVAL_DECIDE", "SETTLEMENT_CONFIRM"),
                Set.of("expo-org-001"),
                true));

        users.put("supplier.pm", new UserAccount(
                "supplier.pm",
                passwordEncoder.encode("ChangeMe123!"),
                Set.of("SUPPLIER_MANAGER", "USER"),
                Set.of("ORDER_FULFILL", "DELIVERY_CONFIRM"),
                Set.of("expo-org-001"),
                true));

        users.put("exhibitor.owner", new UserAccount(
                "exhibitor.owner",
                passwordEncoder.encode("ChangeMe123!"),
                Set.of("EXHIBITOR_OWNER", "USER"),
                Set.of("ORDER_CREATE", "ACCEPTANCE_CONFIRM"),
                Set.of("expo-org-001"),
                true));
    }

    public Optional<UserAccount> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
