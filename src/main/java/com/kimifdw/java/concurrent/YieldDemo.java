package com.kimifdw.java.concurrent;

public class YieldDemo {

    public static void main(String[] args) {
        Thread producer = new Producer();
        Thread consumer = new Consumer();
        // 设置优先级
        producer.setPriority(Thread.MIN_PRIORITY);
        consumer.setPriority(Thread.MAX_PRIORITY);

        producer.start();
        consumer.start();

    }

}

class Producer extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("I am Producer : Producer Item " + i);
            Thread.yield();
        }
    }
}

class Consumer extends Thread {

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("I am Consumer: Consumer Item " + i);
            Thread.yield();
        }
    }
}