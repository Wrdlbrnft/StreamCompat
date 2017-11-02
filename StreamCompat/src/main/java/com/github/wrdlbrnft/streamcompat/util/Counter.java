package com.github.wrdlbrnft.streamcompat.util;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 22/03/16
 */
public class Counter {

    private long mCount;

    public Counter(long initialValue) {
        mCount = initialValue;
    }

    public Counter() {
        this(0);
    }

    public void increment() {
        mCount++;
    }

    public long getCount() {
        return mCount;
    }
}
