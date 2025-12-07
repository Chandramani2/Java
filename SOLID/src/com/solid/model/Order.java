package com.solid.model;
/*
    S - Single Responsibility Principle (SRP)
    Definition: A class should have only one reason to change. It should do one thing well.
    Bad Practice: A single class that handles payment logic, logs transactions to a text file, and sends emails. Good Practice: We separate the logic. Order holds data, PaymentProcessor handles logic.
*/
// SRP: This class is ONLY responsible for holding Order data.
// It does not calculate taxes, save to DB, or print receipts.
public class Order {
    private String item;
    private double amount;

    public Order(String item, double amount) {
        this.item = item;
        this.amount = amount;
    }

    public double getAmount() { return amount; }
    public String getItem() { return item; }
}