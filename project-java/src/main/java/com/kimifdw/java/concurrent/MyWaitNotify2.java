package com.kimifdw.java.concurrent;

/**
 * 解决线程通信中的丢失信号的问题
 * Created by kimiyu on 2017/2/9.
 */
public class MyWaitNotify2 {

    final MontiorObject montiorObject = new MontiorObject();
    // 避免丢失信号
    boolean wasSignalled = false;

    public void doWait() {
        synchronized (montiorObject) {
            if (!wasSignalled) {
                try {
                    montiorObject.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // 没有被通知过，等待通知
            wasSignalled = false;
        }

    }

    public void doNotify() {
        synchronized (montiorObject) {
            // 已经被通知
            wasSignalled = true;
            montiorObject.notify();
        }
    }
}

class MontiorObject {
}
