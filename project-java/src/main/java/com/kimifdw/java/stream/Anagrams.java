package com.kimifdw.java.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        Path path = Paths.get(dictionary.toURI());

        Map<String, Set<String>> groups = new HashMap<>();

        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
            }
        }

        for (Set<String> group : groups.values()) {
            if (group.size() >= minGroupSize) {
                System.out.println(group.size() + ": " + group);
            }
        }

        try(Stream<String> words = Files.lines(path)){
            words.collect(groupingBy(Anagrams::alphabetize))
                    .values().stream()
                    .filter(group->group.size() >=minGroupSize)
                    .forEach(g-> System.out.println(g.size() + ": " + g));
        }
    }

    /**
     * 字符串的字母排序
     * @param s
     * @return
     */
    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);

        return new String(a);
    }
}
