package com.kimifdw.java.collection.ListInf;

import java.util.*;

public class ShuffleDemo {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        for (String arg : args) {
//            list.add(arg);
//        }
        List<String> list = Arrays.asList(args);

        Collections.shuffle(list, new Random());
        System.out.println(list);
    }

    public static <E> void replace(List<E> list, E val, List<? extends E> newVals) {
        for (ListIterator<E> it = list.listIterator(); it.hasNext(); ) {
            if (val == null ? it.next() == null : val.equals(it.next())) {
                it.remove();
                for (E e : newVals) {
                    it.add(e);
                }
            }
        }
    }
}
