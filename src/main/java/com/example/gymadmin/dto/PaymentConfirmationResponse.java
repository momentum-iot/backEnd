package com.example.gymadmin.dto;

import com.example.gymadmin.model.Membership;
import com.example.gymadmin.model.PaymentStatus;

public class PaymentConfirmationResponse {

    private PaymentStatus status;
    private Membership membership;
    private boolean membershipUpdated;

    public PaymentConfirmationResponse() {
    }

    public PaymentConfirmationResponse(PaymentStatus status, Membership membership, boolean membershipUpdated) {
        this.status = status;
        this.membership = membership;
        this.membershipUpdated = membershipUpdated;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public boolean isMembershipUpdated() {
        return membershipUpdated;
    }

    public void setMembershipUpdated(boolean membershipUpdated) {
        this.membershipUpdated = membershipUpdated;
    }
}
