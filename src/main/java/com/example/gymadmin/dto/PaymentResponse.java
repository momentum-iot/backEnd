package com.example.gymadmin.dto;

import com.example.gymadmin.model.Membership;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponse {

    private String sessionId;
    private String checkoutUrl;
    private Long amountInCents;
    private String currency;
    private Membership targetMembership;

    public PaymentResponse() {
    }

    public PaymentResponse(String sessionId, String checkoutUrl, Long amountInCents, String currency,
            Membership targetMembership) {
        this.sessionId = sessionId;
        this.checkoutUrl = checkoutUrl;
        this.amountInCents = amountInCents;
        this.currency = currency;
        this.targetMembership = targetMembership;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    // Alias for frontend: serialize/deserialize as "url"
    @JsonProperty("url")
    public String getUrl() {
        return checkoutUrl;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.checkoutUrl = url;
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

    public Membership getTargetMembership() {
        return targetMembership;
    }

    public void setTargetMembership(Membership targetMembership) {
        this.targetMembership = targetMembership;
    }
}
