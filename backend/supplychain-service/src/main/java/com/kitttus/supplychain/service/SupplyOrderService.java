package com.kitttus.supplychain.service;

import com.kitttus.supplychain.api.dto.CreateOrderRequest;
import com.kitttus.supplychain.api.dto.OrderDetailResponse;
import com.kitttus.supplychain.api.dto.RiskAlertResponse;
import com.kitttus.supplychain.api.dto.TenantDashboardResponse;
import com.kitttus.supplychain.domain.ApprovalStatus;
import com.kitttus.supplychain.domain.SupplyOrder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class SupplyOrderService {
    private final Map<String, SupplyOrder> orders = new ConcurrentHashMap<>();

    public OrderDetailResponse createOrder(String tenantId, CreateOrderRequest request) {
        SupplyOrder order = new SupplyOrder(
                tenantId,
                request.exhibitorId(),
                request.title(),
                request.budgetAmount(),
                request.quoteAmount(),
                request.requiredDate());
        orders.put(order.id(), order);
        return toResponse(order);
    }

    public OrderDetailResponse approveOrder(String orderId, boolean approved) {
        SupplyOrder order = find(orderId);
        order.approve(approved);
        return toResponse(order);
    }

    public OrderDetailResponse signContract(String orderId) {
        SupplyOrder order = find(orderId);
        if (order.approvalStatus() != ApprovalStatus.APPROVED) {
            throw new IllegalStateException("Order must be approved before contract signing");
        }
        order.signContract();
        return toResponse(order);
    }

    public OrderDetailResponse acceptOrder(String orderId) {
        SupplyOrder order = find(orderId);
        if (!order.contractSigned()) {
            throw new IllegalStateException("Contract must be signed before acceptance");
        }
        order.accept();
        return toResponse(order);
    }

    public OrderDetailResponse settleOrder(String orderId) {
        SupplyOrder order = find(orderId);
        if (!order.accepted()) {
            throw new IllegalStateException("Order must be accepted before settlement");
        }
        order.settle();
        return toResponse(order);
    }

    public RiskAlertResponse evaluateRisk(String orderId) {
        SupplyOrder order = find(orderId);
        List<String> warnings = new ArrayList<>();
        if (order.quoteAmount().compareTo(order.budgetAmount()) > 0) {
            warnings.add("超预算预警：报价已超过预算");
        }
        if (LocalDate.now().isAfter(order.requiredDate()) && !order.settled()) {
            warnings.add("延迟预警：需求日期已过但未完成结算");
        }
        return new RiskAlertResponse(order.id(), warnings);
    }

    public TenantDashboardResponse dashboard(String tenantId) {
        var tenantOrders = orders.values().stream().filter(order -> order.tenantId().equals(tenantId)).toList();
        long pending = tenantOrders.stream().filter(order -> order.approvalStatus() == ApprovalStatus.PENDING).count();
        long overBudget = tenantOrders.stream().filter(order -> order.quoteAmount().compareTo(order.budgetAmount()) > 0).count();
        long delayed = tenantOrders.stream().filter(order -> LocalDate.now().isAfter(order.requiredDate()) && !order.settled()).count();
        return new TenantDashboardResponse(tenantId, tenantOrders.size(), pending, overBudget, delayed);
    }

    private SupplyOrder find(String orderId) {
        SupplyOrder order = orders.get(orderId);
        if (order == null) {
            throw new NoSuchElementException("order not found");
        }
        return order;
    }

    private OrderDetailResponse toResponse(SupplyOrder order) {
        return new OrderDetailResponse(
                order.id(),
                order.tenantId(),
                order.exhibitorId(),
                order.title(),
                order.budgetAmount(),
                order.quoteAmount(),
                order.requiredDate(),
                order.approvalStatus(),
                order.orderStatus(),
                order.contractSigned(),
                order.accepted(),
                order.settled());
    }
}
