package com.kimifdw.java.designPattern;

/**
 * 策略模式
 * Created by kimiyu on 17/1/11.
 */
abstract class StrategyPattern {

    // 抽象算法类
    public abstract void AlogorithmInterface();

}

class ConcreteStrategyA extends StrategyPattern {

    @Override
    public void AlogorithmInterface() {
        System.out.printf("算法A实现;");
    }
}

class ConcreteStrategyB extends StrategyPattern {
    @Override
    public void AlogorithmInterface() {
        System.out.printf("算法B实现;");
    }
}

class ConcreteStrateryC extends StrategyPattern {
    @Override
    public void AlogorithmInterface() {
        System.out.printf("算法C实现;");
    }
}

// 只采用策略模式
//class Context {
//    private StrategyPattern strategy;
//
//    public Context(StrategyPattern strategy) {
//        this.strategy = strategy;
//    }
//
//    public void ContextInterface() {
//        this.strategy.AlogorithmInterface();
//    }
//}

// 采用策略模式和简单工厂模式
class Context {
    private StrategyPattern strategy;

    public Context(String type) {
        switch (type) {
            case "A":
                ConcreteStrategyA concreteStrategyA = new ConcreteStrategyA();
                strategy = concreteStrategyA;
                break;
            case "B":
                ConcreteStrategyB concreteStrategyB = new ConcreteStrategyB();
                strategy = concreteStrategyB;
                break;
            case "C":
                ConcreteStrateryC concreteStrateryC = new ConcreteStrateryC();
                strategy = concreteStrateryC;
                break;
        }
    }

    public void ContextInterface() {
        strategy.AlogorithmInterface();
    }
}

class StrategyPatternTest {
    public static void main(String[] args) {
        Context context;

        context = new Context("B");
        context.ContextInterface();

        context = new Context("C");
        context.ContextInterface();

        context = new Context("A");
        context.ContextInterface();
    }
}
