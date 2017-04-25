package com.kimifdw.java.concurrent;

/**
 * Created by kimiyu on 2017/2/9.
 */
public class SynchronizedCounter {

    private long count = 0;

    public synchronized void add(long value) {
        this.count += value;
        System.out.println("线程名称：" + Thread.currentThread().getName() + ",synchronizedCounter:" + count);

    }
}

class CounterThread extends Thread {
    protected SynchronizedCounter synchronizedCounter = null;

    public CounterThread(SynchronizedCounter synchronizedCounter) {
        this.synchronizedCounter = synchronizedCounter;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronizedCounter.add(i);
        }
    }
}

class Example {
    public static void main(String[] args) {
        // 线程调用同一个同步实例，调用add方法会进行阻塞
        SynchronizedCounter synchronizedCounterA = new SynchronizedCounter();
        Thread threadA = new CounterThread(synchronizedCounterA);

        // 线程分别设置不同的实例，调用add方法不会阻塞
        SynchronizedCounter synchronizedCounterB = new SynchronizedCounter();
        Thread threadB = new CounterThread(synchronizedCounterB);

        threadA.start();
        threadB.start();
    }
}
