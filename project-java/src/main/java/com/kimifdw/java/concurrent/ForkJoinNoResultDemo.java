package com.kimifdw.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * forkJoin
 */
public class ForkJoinNoResultDemo {

    public static void main(String[] args) {
        ProductListGenerator productListGenerator = new ProductListGenerator();
        List<Product> products = productListGenerator.generate(10000);

        TaskNoResult task = new TaskNoResult(products, 0, products.size(), 0.2);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(task);

        do {
            System.out.printf("Main: Thread Count: %d\n", forkJoinPool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", forkJoinPool.getStealCount());
            System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
        } while (!task.isDone());

        forkJoinPool.shutdown();

        System.out.printf("Main: End of the program.\n");
    }
}

class ProductListGenerator {

    public List<Product> generate(int size) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(10);
            products.add(product);
        }
        return products;
    }
}

class Product {

    private String name;

    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


}

/**
 * 只处理不返回结果
 */
class TaskNoResult extends RecursiveAction {

    private int first;

    private int last;

    private double increment;

    private List<Product> products;

    public TaskNoResult(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrice();
        } else {
            int middle = (last + first) / 2;
            System.out.printf("TaskNoResult: Pending tasks: %s\n", getQueuedTaskCount());
            TaskNoResult leftHalfTask = new TaskNoResult(products, first, middle + 1, increment);
            TaskNoResult rightHalfTask = new TaskNoResult(products, middle + 1, last, increment);
            invokeAll(leftHalfTask, rightHalfTask);
        }
    }

    private void updatePrice() {
        for (Product product : products) {
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }
}

