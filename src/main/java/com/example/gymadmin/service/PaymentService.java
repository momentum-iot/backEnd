package com.example.gymadmin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.gymadmin.dto.PaymentConfirmationRequest;
import com.example.gymadmin.dto.PaymentConfirmationResponse;
import com.example.gymadmin.dto.PaymentRequest;
import com.example.gymadmin.dto.PaymentResponse;
import com.example.gymadmin.model.Membership;
import com.example.gymadmin.model.Payment;
import com.example.gymadmin.model.PaymentStatus;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.PaymentRepo;
import com.example.gymadmin.repo.UserRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentService {

    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;
    private final String stripeSecretKey;
    private final String frontendUrl;

    public PaymentService(UserRepo userRepo, PaymentRepo paymentRepo,
            @Value("${stripe.secret-key}") String stripeSecretKey,
            @Value("${app.frontend.url}") String frontendUrl) {
        this.userRepo = userRepo;
        this.paymentRepo = paymentRepo;
        this.stripeSecretKey = stripeSecretKey;
        this.frontendUrl = frontendUrl;
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentResponse createMembershipCheckoutSession(Membership membership, User user) throws StripeException {
        if (membership == null) {
            throw new IllegalArgumentException("Membership is required");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }

        long amountInCents = switch (membership) {
            case BASICO -> 8000L;   // S/80.00
            case PREMIUM -> 15000L; // S/150.00
        };

        PaymentRequest request = new PaymentRequest();
        request.setUserId(user.getId());
        request.setTargetMembership(membership);
        request.setAmountInCents(amountInCents);
        request.setCurrency("pen"); // Ajustar si usas otra moneda
        request.setSuccessUrl(frontendUrl + "/pagos/exito");
        request.setCancelUrl(frontendUrl + "/perfil");
        request.setDescription("Membresía " + membership.name());

        return createCheckoutSession(request);
    }

    public PaymentResponse createCheckoutSession(PaymentRequest request) throws StripeException {
        if (stripeSecretKey == null || stripeSecretKey.isBlank()) {
            throw new IllegalStateException("Stripe secret key is not configured");
        }
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(request.getSuccessUrl() + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(request.getCancelUrl())
                .setCustomerEmail(user.getEmail())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(request.getCurrency())
                                                .setUnitAmount(request.getAmountInCents())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Gym membership upgrade")
                                                                .setDescription(request.getDescription() != null
                                                                        ? request.getDescription()
                                                                        : "Upgrade to " + request.getTargetMembership())
                                                                .build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(request.getAmountInCents() / 100.0);
        payment.setCurrency(request.getCurrency());
        payment.setCheckoutSessionId(session.getId());
        payment.setPaymentStatus(PaymentStatus.PENDIENTE);
        payment.setDueDate(LocalDate.now());
        paymentRepo.save(payment);

        return new PaymentResponse(
                session.getId(),
                session.getUrl(),
                request.getAmountInCents(),
                request.getCurrency(),
                request.getTargetMembership());
    }

    public PaymentConfirmationResponse confirmPayment(PaymentConfirmationRequest request) throws StripeException {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Session session = Session.retrieve(request.getSessionId());
        Payment payment = paymentRepo.findByCheckoutSessionId(request.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        if (!payment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Payment does not belong to user");
        }

        if (!"paid".equalsIgnoreCase(session.getPaymentStatus())) {
            return new PaymentConfirmationResponse(PaymentStatus.PENDIENTE, user.getMembership(), false);
        }

        payment.setPaymentStatus(PaymentStatus.PAGADO);
        paymentRepo.save(payment);

        boolean membershipUpdated = false;
        if (user.getMembership() != Membership.PREMIUM) {
            user.setMembership(Membership.PREMIUM);
            userRepo.save(user);
            membershipUpdated = true;
        }

        return new PaymentConfirmationResponse(PaymentStatus.PAGADO, user.getMembership(), membershipUpdated);
    }

    public List<Payment> list() {
        return paymentRepo.findAll();
    }
}
