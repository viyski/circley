package com.gm.circley.widget.groupImageView.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lgm on 16/7/18.
 */
public class KeyGenerator {

    public static String generateMD5(String key) {
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            byte[] bytes = mDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(key.hashCode());
        }
    }

}
