package com.solid.services;
import com.solid.interfaces.PaymentMethod;
/*
    L - Liskov Substitution Principle (LSP)
    Definition: Subtypes must be substitutable for their base types without breaking the application. Scenario: If we have a method processPayment(PaymentMethod pm), we should be able to pass any implementation (CreditCard, PayPal, Bitcoin) and it should work correctly.
    To demonstrate this, we ensure all implementations of PaymentMethod actually perform a payment and don't throw unexpected exceptions (like "NotImplementedException").
 */
public class PayPal implements PaymentMethod {
    private String email;

    public PayPal(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        // LSP: This behaves exactly as the system expects a PaymentMethod to behave.
        System.out.println("Paid " + amount + " using PayPal account: " + email);
    }
}