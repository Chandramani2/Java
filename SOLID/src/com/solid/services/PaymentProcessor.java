package com.solid.services;

import com.solid.interfaces.Notifier;
import com.solid.interfaces.PaymentMethod;
import com.solid.model.Order;

public class PaymentProcessor {

    // DIP: We depend on the Interface (Notifier), not the class (EmailNotifier).
    private Notifier notifier;

    // Constructor Injection
    public PaymentProcessor(Notifier notifier) {
        this.notifier = notifier;
    }

    public void process(Order order, PaymentMethod method) {
        // OCP: This works for ANY PaymentMethod (CreditCard, PayPal, etc.)
        method.pay(order.getAmount());

        // DIP: This works for ANY Notifier (Email, SMS, etc.)
        notifier.sendReceipt("Receipt for " + order.getItem());
    }
}