package com.solid.main;
/*  SolidPaymentSystem/
    ├── src/
    │   └── com/
    │       └── com.solid/
    │           ├── main/
    │           │   └── ShopApp.java            // Entry Point
    │           ├── model/
    │           │   └── Order.java              // Data Class
    │           ├── interfaces/
    │           │   ├── PaymentMethod.java      // For OCP & LSP
    │           │   ├── Refundable.java         // For ISP
    │           │   └── Notifier.java           // For DIP
    │           └── services/
    │               ├── PaymentProcessor.java   // SRP: Logic
    │               ├── EmailNotifier.java      // Low-level module
    │               ├── SMSNotifier.java        // Low-level module
    │               ├── CreditCard.java         // Concrete implementation
    │               └── PayPal.java             // Concrete implementation

 */
import com.solid.interfaces.Notifier;
import com.solid.interfaces.PaymentMethod;
import com.solid.model.Order;
import com.solid.services.*;

public class ShopApp {
    public static void main(String[] args) {

        Order myOrder = new Order("Gaming Keyboard", 150.00);

        // 1. Setup Dependencies (DIP)
        // We can easily swap EmailNotifier with SMSNotifier here without changing logic.
        Notifier myNotifier = new EmailNotifier();

        // 2. Setup Logic
        PaymentProcessor processor = new PaymentProcessor(myNotifier);

        // 3. Select Strategy (OCP & LSP)
        // We can easily swap CreditCard with PayPal.
        PaymentMethod method = new CreditCard();

        // 4. Execute
        processor.process(myOrder, method);
    }
}

/*
    S - Single Responsibility Principle (SRP)
    Definition: A class should have only one reason to change. It should do one thing well.
    Bad Practice: A single class that handles payment logic, logs transactions to a text file, and sends emails. Good Practice: We separate the logic. Order holds data, PaymentProcessor handles logic.

    O - Open/Closed Principle (OCP)
    Definition: Classes should be open for extension but closed for modification. Scenario: If we want to add a new payment method (e.g., Bitcoin), we shouldn't have to modify the core PaymentProcessor class with more if-else statements.

    L - Liskov Substitution Principle (LSP)
    Definition: Subtypes must be substitutable for their base types without breaking the application. Scenario: If we have a method processPayment(PaymentMethod pm), we should be able to pass any implementation (CreditCard, PayPal, Bitcoin) and it should work correctly.
    To demonstrate this, we ensure all implementations of PaymentMethod actually perform a payment and don't throw unexpected exceptions (like "NotImplementedException").

    I - Interface Segregation Principle (ISP)
    Definition: Clients should not be forced to depend on interfaces they do not use. Scenario: Not all payment methods support Refunds. If we put refund() inside the PaymentMethod interface, Bitcoin (which might be irreversible) would be forced to implement a method it can't support.
    Solution: We segregate the interfaces.

    D - Dependency Inversion Principle (DIP)
Definition: High-level modules should not depend on low-level modules. Both should depend on abstractions (interfaces). Scenario: Our PaymentProcessor (High Level) needs to send alerts. It should not depend directly on EmailService (Low Level). If we switch to SMSService later, we shouldn't have to rewrite the Processor.
 */