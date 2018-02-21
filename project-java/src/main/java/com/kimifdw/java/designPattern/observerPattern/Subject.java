package com.kimifdw.java.designPattern.observerPattern;

/**
 *
 */
public interface Subject {
    // 注册或删除观察者
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    // 通知所有观察者
    public void notifyObservers();
}