package com.kimifdw.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 取消forkjoin的task
 */
public class ForkJoinCancelDemo {

    public static void main(String[] args) {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        int[] array = arrayGenerator.generateArray(1000);

        TaskManager taskManager = new TaskManager();

        ForkJoinPool pool = new ForkJoinPool();

        SearchNumberTask task = new SearchNumberTask(array, 0, 1000, 5, taskManager);
        pool.execute(task);

        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.printf("Main: The program has finished\n");
    }
}

class ArrayGenerator {

    public int[] generateArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10);
        }
        return array;
    }
}


class TaskManager {

    private List<ForkJoinTask<Integer>> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(ForkJoinTask<Integer> task) {
        tasks.add(task);
    }

    public void cancelTasks(ForkJoinTask<Integer> cancelTask) {
        for (ForkJoinTask<Integer> task : tasks) {
            if (task != cancelTask) {
                task.cancel(true);
                ((SearchNumberTask) task).writeCancelMessage();
            }
        }
    }
}

class SearchNumberTask extends RecursiveTask<Integer> {

    private int[] numbers;

    private int start, end;

    private int number;

    private TaskManager taskManager;

    private final static int NOT_FOUND = -1;

    public SearchNumberTask(int[] numbers, int start, int end, int number, TaskManager taskManager) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.number = number;
        this.taskManager = taskManager;
    }

    public void writeCancelMessage() {
        System.out.printf("Task: Canceled task from %d to %d", start
                , end);
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Integer compute() {
        System.out.println("Task:" + start + ":" + end);
        int ret;
        if (end - start < 0) {
            ret = lauchTasks();
        } else {
            ret = lookForNumber();
        }
        return ret;
    }

    private int lookForNumber() {
        for (int i = start; i < end; i++) {
            if (numbers[i] == number) {
                System.out.printf("Task：Number %d found in position %d\n", number, i);
                taskManager.cancelTasks(this);
                return i;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return NOT_FOUND;
    }

    private int lauchTasks() {
        int mid = (start + end) / 2;
        SearchNumberTask searchNumberTask1 = new SearchNumberTask(numbers, start, mid, number, taskManager);
        SearchNumberTask searchNumberTask2 = new SearchNumberTask(numbers, mid, end, number, taskManager);
        taskManager.addTask(searchNumberTask1);
        taskManager.addTask(searchNumberTask2);

        searchNumberTask1.fork();
        searchNumberTask2.fork();

        int returnedValue;
        returnedValue = searchNumberTask1.join();
        if (returnedValue != -1) {
            return returnedValue;
        }
        returnedValue = searchNumberTask2.join();
        return returnedValue;
    }
}