package com.coinmasters.controller.auth;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtils {

    private static final int DEFAULT_SALT_LENGTH = 16;

    public static String generateSalt(int length){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String generateSalt(){
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

}
