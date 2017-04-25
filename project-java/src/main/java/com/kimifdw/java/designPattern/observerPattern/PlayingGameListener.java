package com.kimifdw.java.designPattern.observerPattern;

public class PlayingGameListener {
    public PlayingGameListener(){
        System.out.println("playing");
    }

    public void stopPlayingGame(String name){
        System.out.println("stop playing"+name);
    }
}