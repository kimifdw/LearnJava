package com.kimifdw.java.designPattern.observerPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class WeatherData implements Subject {

    private List<java.util.Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public WeatherData() {
        this.observers = new ArrayList<>();
    }

    // 注册观察者
    public void registerObserver(java.util.Observer o) {
        observers.add(o);
    }


    @Override
    public void registerObserver(com.kimifdw.java.designPattern.observerPattern.Observer o) {

    }

    // 移除观察者
    @Override
    public void removeObserver(com.kimifdw.java.designPattern.observerPattern.Observer o) {
        int i = observers.indexOf(o);
        if (i > 0) {
            observers.remove(o);
        }
    }

    /**
     * 通知观察者
     */
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            java.util.Observer observer = (Observer) observers.get(i);
//            observer.update(observer., humidity);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMesurements(float temperature, float humidity, float pressure) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.pressure = pressure;
        measurementsChanged();
    }
}