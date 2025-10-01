package com.sm.virtual;

public class VirtualThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Runnable myRunnable = () -> System.out.println("Inside thread: " + Thread.currentThread());
        // Inside thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1

        Thread virtualThread = Thread.ofVirtual().unstarted(myRunnable);

        virtualThread.start();
        virtualThread.join();
    }
}
