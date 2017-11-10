package com.kimifdw.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 处理有结果的ForkJoin例子
 */
public class ForkJoinResultDemo {

    public static void main(String[] args) {
        Document document = new Document();
        String[][] documents = document.generateDocument(100, 1000, "the");

        DocumentTask documentTask = new DocumentTask(documents, 0, 100, "the");

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(documentTask);

        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", forkJoinPool.getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!documentTask.isDone());

        forkJoinPool.shutdown();

        try {
            System.out.printf("Main: The word appears %d in the document", documentTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}

class Document {

    private String[] words = {
            "the", "hello", "goodbye", "packt", "java", "thread", "pool", "random", "class", "main"
    };

    public String[][] generateDocument(
            int numLines, int numWords, String word
    ) {
        int counter = 0;
        String[][] document = new String[numLines][numWords];
        Random random = new Random();
        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < numWords; j++) {
                int index = random.nextInt(words.length);
                document[i][j] = words[index];
                if (document[i][j].equals(word)) {
                    counter++;
                }
            }
        }
        System.out.println("DocumentMock: The word appears " + counter + " times in the document");
        return document;
    }
}

/**
 * 处理行
 */
class DocumentTask extends RecursiveTask<Integer> {

    private String[][] document;

    private int start, end;

    private String word;

    public DocumentTask(String[][] document, int start, int end, String word) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - start < 10) {
            result = processLines(document, start, end, word);
        } else {
            int middle = (start + end) / 2;
            DocumentTask leftDocumentTask = new DocumentTask(document, start, middle, word);
            DocumentTask rightDocumentTask = new DocumentTask(document, middle, end, word);
            invokeAll(leftDocumentTask, rightDocumentTask);

            try {
                result = groupResults(leftDocumentTask.get(), rightDocumentTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Integer processLines(String[][] document, int start, int end, String word) {
        List<LineTask> lineTasks = new ArrayList<>();
        for (int i = start; i < end; i++) {
            LineTask lineTask = new LineTask(document[i], 0, document[i].length, word);
            lineTasks.add(lineTask);
        }
        invokeAll(lineTasks);

        int result = 0;
        for (LineTask lineTask : lineTasks) {
            try {
                result = result + lineTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Integer groupResults(Integer number1, Integer number2) {
        return number1 + number2;
    }
}

/**
 * 处理行中的单词数
 */
class LineTask extends RecursiveTask<Integer> {

    private String[] line;

    private int start, end;

    private String word;

    public LineTask(String[] line, int start, int end, String word) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        Integer result = null;
        if (end - start < 100) {
            result = count(line, start, end, word);
        } else {
            int mid = (start + end) / 2;
            LineTask task1 = new LineTask(line, start, mid, word);
            LineTask task2 = new LineTask(line, mid, end, word);

            invokeAll(task1, task2);
            try {
                result = groupResult(task1.get(), task2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Integer count(String[] line, int start, int end, String word) {
        int count = 0;
        for (int i = start; i < end; i++) {
            if (line[i].equals(word)) {
                count++;
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count;
    }

    private Integer groupResult(Integer number1, Integer number2) {
        return (number1 == null ? 0 : number1) +
                (number2 == null ? 0 : number2);
    }
}

