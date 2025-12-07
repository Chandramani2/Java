package com.solid.interfaces;
/*
    I - Interface Segregation Principle (ISP)
    Definition: Clients should not be forced to depend on interfaces they do not use. Scenario: Not all payment methods support Refunds. If we put refund() inside the PaymentMethod interface, Bitcoin (which might be irreversible) would be forced to implement a method it can't support.
    Solution: We segregate the interfaces.
 */
// ISP: Only classes that support refunds will implement this.
// We didn't pollute the 'PaymentMethod' interface with this method.
public interface Refundable {
    void refund(double amount);
}