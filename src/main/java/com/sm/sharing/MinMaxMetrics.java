package com.sm.sharing;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long max_value;
    private volatile long min_value;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
        this.max_value = Long.MIN_VALUE;
        this.min_value = Long.MAX_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        // Add code here
        if (newSample > max_value) {
            max_value = newSample;
        }
        if (newSample < min_value) {
            min_value = newSample;
        }
        // 写入可能停留在CPU缓存中，其他线程看不到最新值，所以min_value、max_value要设置为volatile
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        // Add code here
        return min_value;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        // Add code here
        return max_value;
    }
}
