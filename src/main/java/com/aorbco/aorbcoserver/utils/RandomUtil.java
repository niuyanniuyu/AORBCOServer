package com.aorbco.aorbcoserver.utils;

import java.util.Random;

/**
 * @author Ben
 */
public class RandomUtil {
    public static Random random = new Random();


    public static Integer rInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static Integer rInt(int max) {
        return random.nextInt(max + 1);
    }
}
