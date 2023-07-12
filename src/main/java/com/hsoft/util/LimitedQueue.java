package com.hsoft.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LimitedQueue<E> extends ConcurrentLinkedQueue<E> {
    private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public synchronized boolean add(E e) {
        super.add(e);
        while (size() > limit) { super.remove(); }
        return true;
    }

    @Override
    public synchronized boolean offer(E e) {
        super.offer(e);
        while (size() > limit) { super.remove(); }
        return true;
    }

}