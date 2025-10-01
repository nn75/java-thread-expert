package com.sm.virtual;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadsWithBlockingCalls {
    private static final int NUMBER_OF_VIRTUAL_THREADS = 2;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> virtualThreads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(new BlockingTask());
            virtualThreads.add(virtualThread);
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            System.out.println("Inside thread: " + Thread.currentThread() + " before blocking call");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside thread: " + Thread.currentThread() + " after blocking call");

//            Inside thread: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-2 before blocking call
//            Inside thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1 before blocking call
//            Inside thread: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1 after blocking call
//            Inside thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-2 after blocking call
        }
    }
}