package com.kimifdw.java.concurrent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kimiyu on 2017/2/14.
 */
public class BlockingQueueDemo<T> {

    private List queue = new LinkedList();

    private int limit = 10;

    public BlockingQueueDemo(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(Object item) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }

        if (this.queue.size() == 0) {
            notifyAll();
        }

        this.queue.add(item);
    }

    public synchronized void dequeue() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }

        if (this.queue.size() == this.limit) {
            notifyAll();
        }

        this.queue.remove(0);
    }

    public synchronized Object take() throws InterruptedException {
        if (this.queue.size() == 0) {
            return null;
        }
        return this.queue.get(0);
    }

}
