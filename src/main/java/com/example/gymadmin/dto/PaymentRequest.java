package com.example.gymadmin.dto;

import com.example.gymadmin.model.Membership;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Membership targetMembership = Membership.PREMIUM;

    @NotNull
    @Min(1)
    private Long amountInCents;

    @NotBlank
    private String currency;

    @NotBlank
    private String successUrl;

    @NotBlank
    private String cancelUrl;

    private String description;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Membership getTargetMembership() {
        return targetMembership;
    }

    public void setTargetMembership(Membership targetMembership) {
        this.targetMembership = targetMembership;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
