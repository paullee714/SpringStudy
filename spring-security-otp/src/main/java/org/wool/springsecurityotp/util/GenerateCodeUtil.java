package org.wool.springsecurityotp.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {

    public GenerateCodeUtil() {
    }

    public static String generateCode() {
        String code = "";

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            int c = random.nextInt(9000) + 1000;

            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot Generate Random Code");
        }
        return code;
    }
}
