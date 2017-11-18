package com.kimifdw.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * CountDownLatch(倒计时器)的demo，通常用于控制线程等待，可以让某一个线程等待直到倒计时结束，再开始执行。
 */
public class CountDownLatchDemo implements Runnable{
    public long timeTasks(int nThreads,final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i=0;i<nThreads;i++){
            Thread t = new Thread(){
                @Override
                public void run(){
                    try{
                        startGate.await(); // 等待startGate的线程执行完
                        try{
                            task.run();
                        }finally{
                            endGate.countDown();
                        }
                    }catch(InterruptedException ignored){}
                    
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }

    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run(){
        try{
            Thread.sleep(new Random().nextInt(10)*100);
            System.out.println(Thread.currentThread().getName() + "检查完成！");
            end.countDown();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

     public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i=0;i<10;i++){
            executorService.submit(demo);
        }

        // 等待检查完成
        end.await();

        System.out.println("检查完成！");
        executorService.shutdown();

    }
}