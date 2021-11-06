package com.company;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
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
    private static SecretKey GenerateAESKey(int nrBytes){
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

    }
}

class User {
     Server server;
    private Application application;

    User() {
        application = new Application();
    }



}

class Application {
    Server server;

    // RSA public and private keys
    PublicKey publicKey;
    PrivateKey privateKey;

    Cipher decryptCipher;
    Cipher encryptCipher;

    Application() {

        KeyPairGenerator generator = null;

        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();

            decryptCipher = Cipher.getInstance("RSA");
            encryptCipher = Cipher.getInstance("RSA");

            this.decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String secretMessage) {
        String encryptedText = "";
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

    public String decrypt(String encryptedPass) {
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedPass);

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

public class Main {

    public static void main(String[] args) {
        System.out.println("Login:" + "sanatate");
        User user;
        user = new User();

        String encryptedMessage = user.application.encrypt("Buna ziua");
        System.out.println("Mesajul criptat este: " + encryptedMessage);

        System.out.println("Mesajul decriptat: " + user.application.decrypt(encryptedMessage));





    }
}
