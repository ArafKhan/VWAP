package com.hsoft.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LimitedQueue<E> extends ConcurrentLinkedQueue<E> {
    private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E e) {
        synchronized (this) {
            super.add(e);
            while (size() > limit) { super.remove(); }
            return true;
        }
    }

    @Override
    public boolean offer(E e) {
        synchronized (this) {
            super.offer(e);
            while (size() > limit) {
                super.remove();
            }
            return true;
        }
    }

}