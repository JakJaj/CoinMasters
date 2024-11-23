package com.coinmasters.controller.group;

import java.util.Random;

public class GroupUtils {
    private final static String CHARS = "ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoperstuvwxyz1234567890";
    private final static int CODE_LENGTH = 10;
    public static String crateJoinCode(){
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        while (code.length() < CODE_LENGTH){
            int index = (int) (random.nextFloat() * CHARS.length());
            code.append(CHARS.charAt(index));
        }
        return code.toString();
    }
}
