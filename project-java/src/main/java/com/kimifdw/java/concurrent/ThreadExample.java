package com.kimifdw.java.concurrent;

/**
 * Created by kimiyu on 2017/2/8.
 */
public class ThreadExample {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            new Thread("" + i) {
                // run方法并非是由刚创建的新线程所执行的，而是被创建新线程的当前线程锁执行了
                public void run() {
                    System.out.println("Thread:" + getName() + "running");
                }
            }.start();
        }
    }
}
