package com.kimifdw.java.concurrent;

/**
 * 信号量的DEMO，调用顺序为take(),release()
 */
public class SemaphoreDemo {

    private boolean signal = false;
    // 可计数的信号量
    private int signalNew = 0;

    // 有上限的信号量(将bound设为1，可当做锁来使用)
    private int signalBound = 0;
    private int bound = 0;

    /**
     * 获取信号量
     */
    public synchronized void take() {
        this.signal = true;
        this.notify();
    }

    /**
     * 释放信号量
     */
    public synchronized void release() throws InterruptedException {
        // 没有接收到信号量，一直等待
        while (!this.signal) wait();

        this.signal = false;
    }

    /**
     * 可计数的信号量
     */
    public synchronized void takeNew() {
        this.signalNew++;
        this.notify();
    }

    public synchronized void releaseNew() throws InterruptedException {
        while (this.signalNew == 0) wait();
        this.signalNew--;
    }

    public SemaphoreDemo(int upperBound) {
        this.bound = upperBound;
    }

    public synchronized void takeBound() throws InterruptedException {
        // 信号量达到上限，阻塞新信号量的请求
        while (this.signalBound == bound) wait();
        this.signalBound++;
        this.notify();
    }

    public synchronized void releaseBound() throws InterruptedException {
        while (this.signalBound == 0) wait();
        this.signalBound--;
        this.notify();
    }

}