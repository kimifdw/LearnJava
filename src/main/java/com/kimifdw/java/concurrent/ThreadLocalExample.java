package com.kimifdw.java.concurrent;

/**
 * ThreadLocal的demo
 * Created by kimiyu on 2017/2/10.
 */
public class ThreadLocalExample {

    public static class MyRunnable implements Runnable {

        // 设置默认值允许所有线程获取
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 1;
            }
        };

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "-" + threadLocal.get());
            threadLocal.set((int) (Math.random() * 100D));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + threadLocal.get());
        }

    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        Thread threadA = new Thread(myRunnable);
        Thread threadB = new Thread(myRunnable);

        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
