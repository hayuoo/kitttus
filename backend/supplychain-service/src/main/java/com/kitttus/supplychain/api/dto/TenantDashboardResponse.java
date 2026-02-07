package com.kitttus.supplychain.api.dto;

public record TenantDashboardResponse(
        String tenantId,
        long totalOrders,
        long pendingApproval,
        long overBudget,
        long delayed) {
}
