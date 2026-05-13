package com.human.business.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordUtils {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int PARALLELISM = 1;
    private static final int MEMORY = 4096;
    private static final int ITERATIONS = 3;

    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(SALT_LENGTH,HASH_LENGTH,PARALLELISM,MEMORY,ITERATIONS);

    // 加密密码
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 验证密码
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
