package com.kimifdw.java.designPattern.proxyPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 */
public class JDKProxy implements InvocationHandler {

    // 需要代理的目标对象
    private Object targetObject;

    public Object createProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(),
                this.targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 被代理对象
        Pursuit pursuit = (Pursuit) this.targetObject;
        Object result = null;
        // 切面逻辑
        System.out.println("---before invoke----");
        if (pursuit.getClass()!=null){
            result = method.invoke(targetObject,args);
        }
        System.out.println("---after invoke---");
        return result;
    }
}
