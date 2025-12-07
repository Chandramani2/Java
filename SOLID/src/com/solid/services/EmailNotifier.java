package com.solid.services;
import com.solid.interfaces.Notifier;

public class EmailNotifier implements Notifier {
    @Override
    public void sendReceipt(String message) {
        System.out.println("Sending EMAIL: " + message);
    }
}