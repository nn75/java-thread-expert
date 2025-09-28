package com.sm.coordinate;

import java.math.BigInteger;

public class ThreadTermination {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputation(new BigInteger("2000000"), new BigInteger("10000000000")));

        // Even though the long calculation has not finished,
        // just the fact that the main thread ended makes the entire app terminate.
        thread.setDaemon(true);  // true: 目的只是说不阻止主进程退出，而不是保护当前线程不结束.
        thread.start();
        Thread.sleep(100);
        thread.interrupt();  // 如果线程不主动检查中断状态并响应，interrupt()是无效的
    }

    private static class LongComputation implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + "=" + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0 ; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }

            return result;
        }
    }
}
