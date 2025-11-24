package com.example.gymadmin.dto;

import com.example.gymadmin.model.Membership;
import jakarta.validation.constraints.NotNull;

public class MembershipCheckoutRequest {

    @NotNull
    private Membership membership;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
