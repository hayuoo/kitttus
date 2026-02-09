package com.kitttus.supplychain.api.dto;

import com.kitttus.supplychain.domain.ApprovalStatus;
import com.kitttus.supplychain.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderDetailResponse(
        String id,
        String tenantId,
        String exhibitorId,
        String title,
        BigDecimal budgetAmount,
        BigDecimal quoteAmount,
        LocalDate requiredDate,
        ApprovalStatus approvalStatus,
        OrderStatus orderStatus,
        boolean contractSigned,
        boolean accepted,
        boolean settled) {
}
