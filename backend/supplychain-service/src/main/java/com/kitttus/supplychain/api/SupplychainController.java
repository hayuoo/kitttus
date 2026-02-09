package com.kitttus.supplychain.api;

import com.kitttus.supplychain.api.dto.ApprovalDecisionRequest;
import com.kitttus.supplychain.api.dto.CreateOrderRequest;
import com.kitttus.supplychain.api.dto.OrderDetailResponse;
import com.kitttus.supplychain.api.dto.RiskAlertResponse;
import com.kitttus.supplychain.api.dto.TenantDashboardResponse;
import com.kitttus.supplychain.service.SupplyOrderService;
import com.kitttus.supplychain.service.TenantAccessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class SupplychainController {
    private final SupplyOrderService supplyOrderService;
    private final TenantAccessService tenantAccessService;

    public SupplychainController(SupplyOrderService supplyOrderService, TenantAccessService tenantAccessService) {
        this.supplyOrderService = supplyOrderService;
        this.tenantAccessService = tenantAccessService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<TenantDashboardResponse> dashboard(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Authentication authentication) {
        tenantAccessService.assertTenantAccess(authentication, tenantId);
        return ResponseEntity.ok(supplyOrderService.dashboard(tenantId));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDetailResponse> createOrder(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Authentication authentication,
            @Valid @RequestBody CreateOrderRequest request) {
        tenantAccessService.assertTenantAccess(authentication, tenantId);
        return ResponseEntity.ok(supplyOrderService.createOrder(tenantId, request));
    }

    @PostMapping("/orders/{orderId}/approve")
    public ResponseEntity<OrderDetailResponse> approveOrder(
            @PathVariable String orderId,
            @Valid @RequestBody ApprovalDecisionRequest request) {
        return ResponseEntity.ok(supplyOrderService.approveOrder(orderId, request.approved()));
    }

    @PostMapping("/orders/{orderId}/contract")
    public ResponseEntity<OrderDetailResponse> signContract(@PathVariable String orderId) {
        return ResponseEntity.ok(supplyOrderService.signContract(orderId));
    }

    @PostMapping("/orders/{orderId}/acceptance")
    public ResponseEntity<OrderDetailResponse> acceptOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(supplyOrderService.acceptOrder(orderId));
    }

    @PostMapping("/orders/{orderId}/settlement")
    public ResponseEntity<OrderDetailResponse> settleOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(supplyOrderService.settleOrder(orderId));
    }

    @GetMapping("/orders/{orderId}/risk")
    public ResponseEntity<RiskAlertResponse> risk(@PathVariable String orderId) {
        return ResponseEntity.ok(supplyOrderService.evaluateRisk(orderId));
    }
}
