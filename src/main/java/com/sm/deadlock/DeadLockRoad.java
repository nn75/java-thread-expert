package com.sm.deadlock;


import java.util.Random;

public class DeadLockRoad {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();

        // Stuck
//        Road B is locked by thread Thread-1
//        Road A is locked by thread Thread-0
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadA();
            }
        }
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadB();
            }
        }
    }

//    public static class Intersection {
//        private Object roadA = new Object();
//        private Object roadB = new Object();
//
//        public void takeRoadA(){
//            synchronized (roadA) {  // 1. 锁住RoadA：确保只有我这列火车使用RoadA
//                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
//
//                synchronized (roadB) { // 2. 锁住RoadB：确保没有火车使用RoadB（避免碰撞）
//                    System.out.println("Train is passing through road A");
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }
//
//        public void takeRoadB(){
//            synchronized (roadB) {
//                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
//
//                synchronized (roadA) { // acquire roadB to make sure a train cannot go on road B while road A is used
//                    System.out.println("Train is passing through road B");
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }
//    }

    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA(){
            synchronized (roadA) {  // 1. 锁住RoadA：确保只有我这列火车使用RoadA
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());

                synchronized (roadB) { // 2. 锁住RoadB：确保没有火车使用RoadB（避免碰撞）
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public void takeRoadB(){
            synchronized (roadA) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());

                synchronized (roadB) { // acquire roadB to make sure a train cannot go on road B while road A is used
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
