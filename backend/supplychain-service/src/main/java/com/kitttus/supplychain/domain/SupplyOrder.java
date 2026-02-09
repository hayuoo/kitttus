package com.kitttus.supplychain.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class SupplyOrder {
    private final String id;
    private final String tenantId;
    private final String exhibitorId;
    private final String title;
    private final BigDecimal budgetAmount;
    private final BigDecimal quoteAmount;
    private final LocalDate requiredDate;
    private final LocalDateTime createdAt;
    private ApprovalStatus approvalStatus;
    private OrderStatus orderStatus;
    private boolean contractSigned;
    private boolean accepted;
    private boolean settled;

    public SupplyOrder(String tenantId, String exhibitorId, String title, BigDecimal budgetAmount, BigDecimal quoteAmount, LocalDate requiredDate) {
        this.id = UUID.randomUUID().toString();
        this.tenantId = tenantId;
        this.exhibitorId = exhibitorId;
        this.title = title;
        this.budgetAmount = budgetAmount;
        this.quoteAmount = quoteAmount;
        this.requiredDate = requiredDate;
        this.createdAt = LocalDateTime.now();
        this.approvalStatus = ApprovalStatus.PENDING;
        this.orderStatus = OrderStatus.CREATED;
    }

    public String id() { return id; }
    public String tenantId() { return tenantId; }
    public String exhibitorId() { return exhibitorId; }
    public String title() { return title; }
    public BigDecimal budgetAmount() { return budgetAmount; }
    public BigDecimal quoteAmount() { return quoteAmount; }
    public LocalDate requiredDate() { return requiredDate; }
    public LocalDateTime createdAt() { return createdAt; }
    public ApprovalStatus approvalStatus() { return approvalStatus; }
    public OrderStatus orderStatus() { return orderStatus; }
    public boolean contractSigned() { return contractSigned; }
    public boolean accepted() { return accepted; }
    public boolean settled() { return settled; }

    public void approve(boolean approved) {
        this.approvalStatus = approved ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
    }

    public void signContract() {
        this.contractSigned = true;
        this.orderStatus = OrderStatus.CONTRACTED;
    }

    public void accept() {
        this.accepted = true;
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void settle() {
        this.settled = true;
        this.orderStatus = OrderStatus.SETTLED;
    }
}
