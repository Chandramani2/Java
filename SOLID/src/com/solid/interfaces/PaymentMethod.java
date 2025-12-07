package com.solid.interfaces;

/*
    O - Open/Closed Principle (OCP)
    Definition: Classes should be open for extension but closed for modification. Scenario: If we want to add a new payment method (e.g., Bitcoin), we shouldn't have to modify the core PaymentProcessor class with more if-else statements.

 */
// OCP: We define a contract. New payment types will implement this.
// We don't need to change existing code to add new payment types.
public interface PaymentMethod {
    void pay(double amount);
}