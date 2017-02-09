package com.kimifdw.java.concurrent;

/**
 * 防止假唤醒
 * Created by kimiyu on 2017/2/9.
 */
public class MyWaitNotify3 {

    final MonitorObject monitorObject = new MonitorObject();
    Boolean wasSignalled = false;

    public void doWait() {
        synchronized (monitorObject) {
            // 自旋锁
            while (!wasSignalled) {
                try {
                    monitorObject.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (monitorObject) {
            wasSignalled = true;
            monitorObject.notify();
        }
    }
}

class MonitorObject {

}
