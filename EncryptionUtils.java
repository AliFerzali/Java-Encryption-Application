package com.example.encryption;

import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionUtils {
    // Definiera algoritmnamn
    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";

    // Generera en AES-nyckel med angiven längd
    public static SecretKey generateAESKey(int keyLength) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(keyLength);  // Initiera nyckelgenerering med önskad längd
        return keyGenerator.generateKey(); // Generera AES-nyckeln
    }

    // Kryptera en vanlig text med AES-nyckel
    public static String encryptAES(String plainText, SecretKey key) throws Exception {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM); // Hämta AES-chifferinstans
        cipher.init(Cipher.ENCRYPT_MODE, key); // Initiera cipher i krypteringsläge med nyckeln
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes); // Kryptera bytes
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    // Avkryptera en med AES krypterad text med AES-nyckel
    public static String decryptAES(String encryptedText, SecretKey key) throws Exception {
        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT); // Avkoda Base64-kodad text
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM); // Hämta AES-chifferinstans
        cipher.init(Cipher.DECRYPT_MODE, key); // Initiera chiffer i dekrypteringsläge med nyckeln
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // Avkryptera byten
        return new String(decryptedBytes, StandardCharsets.UTF_8); // Konvertera avkrypterade byten till sträng
    }

    // Generera ett RSA-nyckelpar med angiven längd
    public static KeyPair generateRSAKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        SecureRandom secureRandom = new SecureRandom(); // Skapa säker slumpgenerator för nyckelgenerering
        keyPairGenerator.initialize(keyLength, secureRandom); // Initiera nyckelgenerering med önskad längd och slump
        return keyPairGenerator.generateKeyPair(); // Generera RSA-nyckelparet
    }

    // Kryptera en vanlig text med RSA offentlig nyckel
    public static String encryptRSA(String plainText, PublicKey publicKey) throws Exception {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM); // Hämta RSA-chifferinstans
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Initiera chiffer i krypteringsläge med offentlig nyckel
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes); // Kryptera byten
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    // Avkryptera en med RSA krypterad text med RSA privat nyckel
    public static String decryptRSA(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT); // Avkoda Base64-kodad text
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM); // Hämta RSA-chifferinstans
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // Initiera chiffer i dekrypteringsläge med privat nyckel
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // Avkryptera byten
        return new String(decryptedBytes, StandardCharsets.UTF_8); // Konvertera avkrypterade byten till sträng
    }
}

// Krpytering appen och koden är skriven av Ali Ferzali.
