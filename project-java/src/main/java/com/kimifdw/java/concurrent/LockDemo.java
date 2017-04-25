package com.kimifdw.java.concurrent;
/**
 * LockDemo
 * 
 * */
public class LockDemo{

    private boolean isLocked = false;

    private Thread lockingThread = null;

    public synchronized void lock() throws InterruptedException{
        
        while(isLocked){
            wait(); // 释放锁实例对应的同步锁，允许其他线程进入lock()方法
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unLocked() {
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }

        isLocked = false;
        lockingThread = null;
        notify();
    }
}