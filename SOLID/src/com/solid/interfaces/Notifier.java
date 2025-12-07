package com.solid.interfaces;
/*
    D - Dependency Inversion Principle (DIP)
    Definition: High-level modules should not depend on low-level modules. Both should depend on abstractions (interfaces). Scenario: Our PaymentProcessor (High Level) needs to send alerts. It should not depend directly on EmailService (Low Level). If we switch to SMSService later, we shouldn't have to rewrite the Processor.
 */
public interface Notifier {
    void sendReceipt(String message);
}