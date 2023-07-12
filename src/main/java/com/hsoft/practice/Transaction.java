package com.hsoft.practice;

public class Transaction {
    private final long quantity;
    private final double price;

    public Transaction(long quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getAmount() {
        return price * quantity;
    }
}
