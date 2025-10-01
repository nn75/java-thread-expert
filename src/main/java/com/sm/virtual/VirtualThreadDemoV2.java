package com.sm.virtual;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadDemoV2 {

    private static final int NUMBER_OF_VIRTUAL_THREADS = 1000;  // 最多能看到12个 platform threads 处理这些任务，和我电脑的核数有关

    public static void main(String[] args) throws InterruptedException {
        Runnable myRunnable = () -> System.out.println("Inside thread: " + Thread.currentThread());
        // Inside thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1

        List<Thread> virtualThreads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(myRunnable);
            virtualThreads.add(virtualThread);
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }
}
