package com.volmit.iris.util;

public class ShortNoiseCache {
    private final int width;
    private final int height;
    private final short[] cache;

    public ShortNoiseCache(int width, int height)
    {
        this.width = width;
        this.height = height;
        cache = new short[width * height];
    }

    public void set(int x, int y, short v) {
        this.cache[y % this.height * this.width + x % this.width] = v;
    }

    public short get(int x, int y) {
        return this.cache[y % this.height * this.width + x % this.width];
    }
}