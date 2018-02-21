package com.kimifdw.java.designPattern.observerPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个事件管理队列的类
 */
public class EventHandler {

    private List<Event> objects;

    public EventHandler() {
        this.objects = new ArrayList<>();
    }

    public void addEvent(Object object, String methodName, Object... args) {
        objects.add(new Event(object, methodName, args));
    }

    public void notifyX() throws Exception {
        for (Event event : objects) {
            event.invoke();
        }
    }
}
