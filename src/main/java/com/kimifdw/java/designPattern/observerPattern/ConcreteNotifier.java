package com.kimifdw.java.designPattern.observerPattern;

/**
 * 具体实现的通知类
 */
public class ConcreteNotifier extends Notifier{

    @Override
    public void addListener(Object object,String methodName,Object... args){
        this.getEventHandler().addEvent(object, methodName, args);
    }

    @Override
    public void notifyX(){
        try{
            this.getEventHandler().notifyX();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}