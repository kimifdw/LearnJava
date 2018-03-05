package com.kimifdw.java.designPattern.proxyPattern;

public class ProxyPatternDemo {

    public static void main(String[] args) {
        SchoolGirl jiaojiao = new SchoolGirl();
        jiaojiao.setName("李娇娇");

//        Pursuit zhuojiayi = new Pursuit(jiaojiao);

        Proxy zhuojiayi = new Proxy(jiaojiao);

        zhuojiayi.giveDolls();
        zhuojiayi.giveFlowers();
        zhuojiayi.giveChocolate();
    }
}
