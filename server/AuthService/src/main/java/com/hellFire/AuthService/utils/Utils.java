package com.hellFire.AuthService.utils;

import java.security.SecureRandom;

public class Utils {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateOtp() {
        int otp = secureRandom.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

}
