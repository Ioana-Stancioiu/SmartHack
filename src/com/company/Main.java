package com.company;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

class Server {
    Cipher encryptCipher;
    PublicKey publicKey;

    public Server(PublicKey publicKey) {
            try {
                encryptCipher = Cipher.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }

            this.publicKey = publicKey;
    }


}

class Encrypt {
    public static SecretKey GenerateAesKey(int nrBytes) throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(nrBytes);
        return keyGenerator.generateKey();
    }

    public static SecretKey Generate3DesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(112);
        return keyGenerator.generateKey();
    }

    public static KeyPair GenerateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        return keyGenerator.generateKeyPair();
    }

    public static KeyPair GenerateECCKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("EC");
        keyGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        return keyGenerator.generateKeyPair();
    }

    public static String encryptRSA(String secretMessage, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        String encryptedText = "";
        Cipher encryptCipher;
        encryptCipher = Cipher.getInstance("RSA");

        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        try {
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            encryptedText = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    public static String decryptRSA(String encryptedPass, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher decryptCipher;
        decryptCipher = Cipher.getInstance("RSA");
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedPass);

        try {
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        String decryptedMessage = "";
        try {
            byte[] decryptedMessageBytes;
            decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
            decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedMessage;
    }
}

class User {
    private Application application;

    User() {
        application = new Application();
    }

    public Application getApplication() {
        return application;
    }
}

class Application {
    public Server server;
    // RSA public and private keys
    PublicKey publicKey;
    PrivateKey privateKey;

    Application() {

        KeyPairGenerator generator = null;

        KeyPair pair = null;
        try {
            pair = Encrypt.GenerateRSAKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public String decryptMessage(String message) {
        try {
            return Encrypt.decryptRSA(message, privateKey);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String encryptMessage(String message) {
        try {
            return Encrypt.encryptRSA(message, publicKey);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}

public class Main {

    public static void main(String[] args) {
        System.out.println("Login:" + "sanatate");
        User user;
        user = new User();

        String encryptedMessage = user.getApplication().encryptMessage("Buna ziua");
        System.out.println("Mesajul criptat este: " + encryptedMessage);

        System.out.println("Mesajul decriptat: " + user.getApplication().decryptMessage(encryptedMessage));
    }
}
