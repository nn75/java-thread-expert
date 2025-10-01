package com.sm.io.bound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOBoundApplication {
    private static final int NUMBER_OF_TASKS = 1000;
    // 如果数量太大，比如10_000，会出现 unable to create native thread: possibly out of memory or process/resource limits reached

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // dynamic pool, cache those threads to be reused in the future
        // we should expect that this thread pool will grow to about 1000 threads.

        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    blockingIoOperation();
                }
            });
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
