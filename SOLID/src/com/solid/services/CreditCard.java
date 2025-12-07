package com.solid.services;
import com.solid.interfaces.PaymentMethod;

/*
    O - Open/Closed Principle (OCP)
    Definition: Classes should be open for extension but closed for modification. Scenario: If we want to add a new payment method (e.g., Bitcoin), we shouldn't have to modify the core PaymentProcessor class with more if-else statements.

 */
public class CreditCard implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}