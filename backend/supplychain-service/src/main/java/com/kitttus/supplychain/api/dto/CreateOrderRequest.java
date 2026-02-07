package com.kitttus.supplychain.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateOrderRequest(
        @NotBlank String exhibitorId,
        @NotBlank String title,
        @NotNull @DecimalMin("0.01") BigDecimal budgetAmount,
        @NotNull @DecimalMin("0.01") BigDecimal quoteAmount,
        @NotNull @Future LocalDate requiredDate) {
}
