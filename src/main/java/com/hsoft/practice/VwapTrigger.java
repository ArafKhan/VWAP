package com.hsoft.practice;


import com.hsoft.api.MarketDataListener;
import com.hsoft.api.PricingDataListener;
import com.hsoft.api.VwapTriggerListener;
import com.hsoft.util.LimitedQueue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Entry point for the candidate to resolve the exercise
 */
public class VwapTrigger implements PricingDataListener, MarketDataListener {

    private final VwapTriggerListener vwapTriggerListener;
    private final int LastNumberOfTransaction = 5;
    private final Map<String, Queue<Transaction>> marketDataMap = new ConcurrentHashMap<>();
    private final Map<String, Double> fairValueMap = new ConcurrentHashMap<>();
    private final Map<String, Double> vwapMap = new ConcurrentHashMap<>();

    /**
     * This constructor is mainly available to ease unit test by not having to provide a VwapTriggerListener
     */
    protected VwapTrigger() {
        this.vwapTriggerListener = (productId, vwap, fairValue) -> {
            // ignore
        };
    }

    public VwapTrigger(VwapTriggerListener vwapTriggerListener) {
        this.vwapTriggerListener = vwapTriggerListener;
    }

    @Override
    public void transactionOccurred(String productId, long quantity, double price) {
        // This method will be called when a new transaction is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'
        marketDataMap.putIfAbsent(productId, new LimitedQueue<>(LastNumberOfTransaction));
        Queue<Transaction> queue = marketDataMap.get(productId);
        queue.add(new Transaction(quantity, price));
        double vwap = computeVwap(queue);
        vwapMap.put(productId, vwap);
        compareVwapAndFairValue(productId);
    }

    @Override
    public void fairValueChanged(String productId, double fairValue) {
        // This method will be called when a new fair value is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'
        fairValueMap.put(productId, fairValue);
        compareVwapAndFairValue(productId);
    }

    protected void compareVwapAndFairValue(String productId) {
        double vwap = vwapMap.getOrDefault(productId, -1.0);
        double fairValue = fairValueMap.getOrDefault(productId, -1.0);
        if (vwap == -1.0 || fairValue == -1.0) return;
        if (vwap > fairValue) this.vwapTriggerListener.vwapTriggered(productId, vwap, fairValue);
    }

    protected double computeVwap(Queue<Transaction> queue) {
        if (queue.size() == 0) return -1.0;
        Double totalAmount = 0.0, totalQuantity = 0.0;
        for (Transaction t : queue) {
            totalAmount += t.getAmount();
            totalQuantity += t.getQuantity();
        }
        return totalAmount / totalQuantity;
    }
}