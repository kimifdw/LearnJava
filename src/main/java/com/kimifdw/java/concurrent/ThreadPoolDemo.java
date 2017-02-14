package com.kimifdw.java.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程池实例
 */
public class ThreadPoolDemo {

    private BlockingQueueDemo taskQueue = null;

    private List<PoolThread> threads = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPoolDemo(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new BlockingQueueDemo(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            threads.add(new PoolThread(taskQueue));
        }

        for (PoolThread thread : threads) {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw new IllegalStateException("ThreadPool is stopped");
        try {
            this.taskQueue.enqueue(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean stop() {
        this.isStopped = true;
        for (PoolThread thread : threads) {
            thread.stop();
        }
        return isStopped;
    }
}

/**
 * 执行线程
 */
class PoolThread extends Thread {

    private BlockingQueueDemo<Runnable> taskQueue = null;

    private boolean isStopped = false;

    public PoolThread(BlockingQueueDemo<Runnable> queue) {
        taskQueue = queue;
    }

    public void run() {
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.take();
                runnable.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void toStop() {
        isStopped = true;
        this.interrupt(); // 打断池中线程的dequeue() 调用
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}