package com.feng.user_manager.controller;

import java.util.HashMap;

public class Test {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;

    public static void main(String[] args) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        System.out.println(DEFAULT_INITIAL_CAPACITY);
        System.out.println(MAXIMUM_CAPACITY);
        System.out.println(DEFAULT_LOAD_FACTOR);
        System.out.println(TREEIFY_THRESHOLD);
        System.out.println(UNTREEIFY_THRESHOLD);
        System.out.println(MIN_TREEIFY_CAPACITY);
    }
}
