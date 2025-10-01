package com.sm.io.bound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * CPU核心: [Thread-1] → [Thread-2] → [Thread-3] → ... → [Thread-1000] → [Thread-1] → ...
 *          ↑           ↑           ↑                   ↑               ↑
 *        每个线程     每个线程     每个线程             循环回到        无限循环
 *        执行一会儿   执行一会儿   执行一会儿           第一个线程       切换！
 */

public class IoBoundApplicationV2 {
    private static final int NUMBER_OF_TASKS = 10_000;

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {

            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 100; j++) {
                            blockingIoOperation();
                        }
                    }
                });
            }
        }
    }

    // Simulates a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
