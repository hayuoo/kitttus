package com.kitttus.supplychain.api.dto;

import jakarta.validation.constraints.NotNull;

public record ApprovalDecisionRequest(@NotNull Boolean approved, String comment) {
}
