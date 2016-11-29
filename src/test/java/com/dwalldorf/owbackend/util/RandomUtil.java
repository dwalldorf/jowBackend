package com.dwalldorf.owbackend.util;

import java.util.Random;

public class RandomUtil {

    private final static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
