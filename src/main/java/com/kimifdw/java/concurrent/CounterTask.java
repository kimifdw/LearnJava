package com.kimifdw.java.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join例子
 * Created by kimiyu on 2017/2/6.
 */
public class CounterTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CounterTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            CounterTask leftTask = new CounterTask(start, middle);
            CounterTask rightTask = new CounterTask(middle + 1, end);

            // 执行子任务，重新调用compute方法对任务进行拆分
            leftTask.fork();
            rightTask.fork();

            // 等待子任务执行完成，并得到其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            // 合并子任务
            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CounterTask counterTask = new CounterTask(1, 4);

        Future<Integer> result = forkJoinPool.submit(counterTask);
        try {
            // 异常处理
            if (counterTask.isCompletedAbnormally()) {
                System.out.println(counterTask.getException());
            }
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException EX) {
            EX.printStackTrace();
        }
    }
}
