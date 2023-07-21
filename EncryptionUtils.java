package com.example.encryption;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionUtils {
    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";

    // Generera AES-nyckel med angiven nyckellängd
    public static SecretKey generateAESKey(int keyLength) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(keyLength);
        return keyGenerator.generateKey();
    }

    // Kryptera text med AES
    public static String encryptAES(String plainText, SecretKey key) throws Exception {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    // Dekryptera text med AES
    public static String decryptAES(String encryptedText, SecretKey key) throws Exception {
        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Generera RSA-nyckelpar med angiven nyckellängd
    public static KeyPair generateRSAKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    // Kryptera text med RSA
    public static String encryptRSA(String plainText, PublicKey publicKey) throws Exception {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    // Dekryptera text med RSA
    public static String decryptRSA(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
