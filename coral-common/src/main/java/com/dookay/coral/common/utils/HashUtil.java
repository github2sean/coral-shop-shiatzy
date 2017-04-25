package com.dookay.coral.common.utils;

import java.security.MessageDigest;

/**
 * Created by YinQichao on 2017-03-09.
 */
public class HashUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String Sha1Hash(String str) {
        String algorithm = "SHA1";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            byte[] codeByte = messageDigest.digest();
            int len = codeByte.length;
            StringBuilder buf = new StringBuilder(len * 2);
            for (int j = 0; j < len; j++) {
                buf.append(HEX_DIGITS[(codeByte[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[codeByte[j] & 0x0f]);
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long BasicHash(String str) {
        long hash = 0;
        char[] charArr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + charArr[i];

            long x;
            if ((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
            }
            hash &= ~x;
        }
        return hash;
    }

    public static <T> long TypeHash(Class<T> type) {
        String className = type.getName();
        return BasicHash(className);
    }
}
