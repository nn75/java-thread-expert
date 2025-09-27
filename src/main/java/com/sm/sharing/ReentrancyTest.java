package com.sm.sharing;

public class ReentrancyTest {
    private final Object lock = new Object();

    public void testReentrancy() {
        synchronized(lock) {
            System.out.println("第一次获得锁");

            synchronized(lock) {
                System.out.println("第二次获得同一个锁");

                synchronized(lock) {
                    System.out.println("第三次获得同一个锁");
                }
            }
        }

        System.out.println("测试完成 - 没有死锁！");
    }

    public static void main(String[] args) {
        new ReentrancyTest().testReentrancy();
    }
}
