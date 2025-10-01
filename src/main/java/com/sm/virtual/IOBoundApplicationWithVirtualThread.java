package com.sm.virtual;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Virtual Thread 解决了因为 thread 太多，application crash 的问题
 * 对照组：{@link com.sm.io.bound.IOBoundApplication}
 */
public class IOBoundApplicationWithVirtualThread {
    private static final int NUMBER_OF_TASKS = 10_000;

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(()-> blockingIoOperation());
            }
        }
    }

    // Simulate a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
