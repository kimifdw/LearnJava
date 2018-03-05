package com.kimifdw.java.designPattern.proxyPattern;

public class Pursuit implements IGiveGift {

    private SchoolGirl mm;

    public Pursuit(SchoolGirl mm) {
        this.mm = mm;
    }

    @Override
    public void giveDolls(){
        System.out.println(mm.getName() + "送你洋娃娃");
    }

    @Override
    public void giveFlowers(){
        System.out.println(mm.getName() + " 送你鲜花");
    }

    @Override
    public void giveChocolate(){
        System.out.println(mm.getName() + " 送你巧克力");
    }
}
