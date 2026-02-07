package com.kitttus.supplychain.api.dto;

import java.util.List;

public record RiskAlertResponse(String orderId, List<String> warnings) {
}
