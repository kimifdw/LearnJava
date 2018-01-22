package com.kimifdw.java.collection.MapInf;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {

        Map<String, Integer> m = new HashMap<>();

        for (String a : args) {
            Integer freq = m.get(a);
            m.put(a, (null == freq) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " distinct words:");
        System.out.println(m);
    }
}
