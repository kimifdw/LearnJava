package com.kimifdw.java.designPattern.observerPattern;

 /**
 * 观察者接口
 */
public interface Observer {
    public void update(float temp,float humidity,float pressure);
}