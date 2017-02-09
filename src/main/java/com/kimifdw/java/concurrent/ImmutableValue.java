package com.kimifdw.java.concurrent;

/**
 * 不可变的共享对象，引用不是线程安全的
 * Created by kimiyu on 2017/2/9.
 */
public class ImmutableValue {

    private int value = 0;

    public ImmutableValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    // 修改值
    public ImmutableValue add(int newValue) {
        return new ImmutableValue(this.value + newValue);
    }
}
