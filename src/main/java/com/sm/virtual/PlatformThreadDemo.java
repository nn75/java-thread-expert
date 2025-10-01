package com.sm.virtual;

public class PlatformThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> System.out.println("Inside thread: " + Thread.currentThread());

        Thread platformThread = Thread.ofPlatform().unstarted(runnable);

        platformThread.start();
        platformThread.join();
    }

}
