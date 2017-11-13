package com.kimifdw.java.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoin异步处理
 */
public class ForkjoinAsyncResultDemo {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FolderProcessor system = new FolderProcessor("/Users/kimiyu", "log");
        FolderProcessor documents = new FolderProcessor("/Users/kimiyu/Project", "java");
        FolderProcessor apps = new FolderProcessor("/Users/kimiyu/Project/sjesnew", "log");

        pool.execute(system);
        pool.execute(documents);
        pool.execute(apps);

        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.
                    getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.
                    getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.
                    getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.
                    getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!system.isDone()) || (!apps.isDone()) || (!documents.
                isDone()));

        pool.shutdown();

        List<String> results;
        results = system.join();
        System.out.printf("System: %d files found.\n", results.size());
        results = apps.join();
        System.out.printf("Apps: %d files found.\n", results.size());
        results = documents.join();
        System.out.printf("Documents: %d files found.\n", results.
                size());

    }
}

class FolderProcessor extends RecursiveTask<List<String>> {

    private static final long serialVersionUID = 1L;

    private String path;

    private String extension;

    public FolderProcessor(String path, String extension) {
        this.extension = extension;
        this.path = path;
    }

    @Override
    protected List<String> compute() {
        List<String> list = new ArrayList<>();
        List<FolderProcessor> folderProcessors = new ArrayList<>();
        File file = new File(path);
        File content[] = file.listFiles();

        if (content != null && content.length > 0) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    FolderProcessor folderProcessor = new FolderProcessor(content[i].getAbsolutePath(), extension);
                    folderProcessor.fork();
                    folderProcessors.add(folderProcessor);
                } else {
                    if (checkFile(content[i].getName())) {
                        list.add(content[i].getAbsolutePath());
                    }
                }
            }
        }
        if (folderProcessors.size() > 50) {
            System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), folderProcessors.size());
        }
        return list;
    }

    private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
        for (FolderProcessor task : tasks) {
            list.addAll(task.join());
        }
    }

    private boolean checkFile(String name) {
        return name.endsWith(extension);
    }
}
