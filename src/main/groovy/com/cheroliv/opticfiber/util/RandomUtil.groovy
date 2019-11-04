package com.cheroliv.opticfiber.util

import org.apache.commons.lang3.RandomStringUtils

import java.security.SecureRandom

final class RandomUtil {

    private static final int DEF_COUNT = 20

    private static final SecureRandom SECURE_RANDOM

    static {
        SECURE_RANDOM = new SecureRandom()
        SECURE_RANDOM.nextBytes(new byte[64])
    }

    private static String generateRandomAlphanumericString() {
        RandomStringUtils.random(
                DEF_COUNT,
                0,
                0,
                true,
                true,
                null,
                SECURE_RANDOM)
    }

    static String generatePassword() {
        generateRandomAlphanumericString()
    }


    static String generateActivationKey() {
        generateRandomAlphanumericString()
    }

    static String generateResetKey() {
        generateRandomAlphanumericString()
    }
}
