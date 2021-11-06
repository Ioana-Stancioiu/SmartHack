package CryptoFunctions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {
    public static String hashString(final String originalString) {
        final MessageDigest digest;
        String result = "";

        try {
            digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashBytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            result = bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
