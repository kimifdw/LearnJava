package com.kimifdw.java.concurrent;

/**
 * join实例
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("First task started");
                System.out.println("Sleeping for 2 seconds");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("First task completed");
            }
        });

        Thread t2 = new Thread(() -> System.out.println("Second task completed"));

        t.start();
        t2.start();
        t.join(); // 等待t2线程完成后再执行t线程
    }
}