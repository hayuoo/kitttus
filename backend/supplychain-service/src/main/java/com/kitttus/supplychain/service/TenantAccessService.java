package com.kitttus.supplychain.service;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TenantAccessService {

    public void assertTenantAccess(Authentication authentication, String tenantId) {
        Object details = authentication.getDetails();
        if (details instanceof Claims claims) {
            Collection<?> tenantIds = claims.get("tenantIds", Collection.class);
            if (tenantIds != null && tenantIds.stream().map(String::valueOf).anyMatch(tenantId::equals)) {
                return;
            }
        }
        throw new AccessDeniedException("Tenant access denied");
    }
}
