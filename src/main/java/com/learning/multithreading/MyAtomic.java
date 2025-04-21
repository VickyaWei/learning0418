package com.learning.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomic {
    AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }
}
