package com.kimifdw.java.designPattern;

/**
 * 简单工厂模式
 * Created by kimiyu on 17/1/8.
 */
public class SimpleFactory {

    // 由一个类根据不同条件创建实例
    public static Operation createOperate(String operate) {
        Operation operation = null;
        switch (operate) {
            case "+":
                operation = new OperationAdd();
                break;
            case "-":
                operation = new OperationSub();
                break;
            case "*":
                operation = new OperationMul();
                break;
            case "/":
                operation = new OperationDiv();
                break;
        }
        return operation;
    }
}

abstract class Operation {

    private double _numberA = 0;
    private double _numberB = 0;

    public double get_numberA() {
        return _numberA;
    }

    public void set_numberA(double numberA) {
        this._numberA = numberA;
    }

    public double get_numberB() {
        return _numberB;
    }

    public void set_numberB(double _numberB) {
        this._numberB = _numberB;
    }

    public double getResult() {
        double result = 0;
        return result;
    }
}

class OperationAdd extends Operation {

    @Override
    public double getResult() {
        return get_numberA() + get_numberB();
    }
}

class OperationSub extends Operation {
    @Override
    public double getResult() {
        return get_numberA() - get_numberB();
    }
}

class OperationMul extends Operation {
    @Override
    public double getResult() {
        return get_numberA() * get_numberB();
    }
}

class OperationDiv extends Operation {
    @Override
    public double getResult() {
        if (get_numberB() == 0) {
            try {
                throw new Exception("除数不能为0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return get_numberA() / get_numberB();
    }
}

class SimpleFactoryTest {
    public static void main(String[] args) {
        Operation operation = SimpleFactory.createOperate("-");
        operation.set_numberA(1);
        operation.set_numberB(2);
        System.out.println(operation.getResult());
    }
}


