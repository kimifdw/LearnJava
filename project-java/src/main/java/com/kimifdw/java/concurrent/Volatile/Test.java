package com.kimifdw.java.concurrent.Volatile;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private volatile int inc = 0;

    private int incNew = 0;

    // 无法保证increase的原子性
    private void increase() {
        inc++;
    }

    // 解决方案：
    // 1. 加同步锁保证原子性
    public synchronized void increaseSync() {
        incNew++;
    }

    // 2. 加锁
    Lock lock = new ReentrantLock();

    public void increaseLock() {
        lock.lock();
        try {
            incNew++;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        final Test test = new Test();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.increase();
//                    test.increaseSync();
                    test.increaseLock();
                }
            }).start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.inc);
        System.out.println("inc sync:" + test.incNew);
    }
}
