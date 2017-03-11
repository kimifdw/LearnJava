package com.kimifdw.java.designPattern.observerPattern;

import java.util.Date;

public class test {

    public static void main(String[] args) {
        Notifier goodNotifier = new ConcreteNotifier();

        PlayingGameListener playingGameListener = new PlayingGameListener();

        WatchingTVListener watchingTVListener = new WatchingTVListener();

        goodNotifier.addListener(playingGameListener, "stopPlayingGame", "测试");

        goodNotifier.addListener(watchingTVListener, "stopWatchingTV", new Date());

        goodNotifier.notifyX();
    }
}