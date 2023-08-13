package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;


public class MainActivity extends AppCompatActivity {
    private EditText inputMessage;
    private EditText ciphEdt;
    private EditText key_dt;
    private EditText algorithm;
    private Button btnEncrypt;
    private Button btnDecrypt;
    private TextView tV1;

    private SecretKey aesKey;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Koppla XML-elementer till Java-variabler
        inputMessage = findViewById(R.id.inputMessage);
        ciphEdt = findViewById(R.id.ciphEdt);
        key_dt = findViewById(R.id.key_dt);
        algorithm = findViewById(R.id.algorithm);
        btnEncrypt = findViewById(R.id.btnencrypt);
        btnDecrypt = findViewById(R.id.btndecrypt);
        tV1 = findViewById(R.id.tV1);

        try {
            // Lyssnare för krypteringsknapp
            btnEncrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String originalText = inputMessage.getText().toString().trim();
                    String cipherText = ciphEdt.getText().toString().trim();
                    String keyLengthStr = key_dt.getText().toString().trim();
                    String selectedAlgorithm = algorithm.getText().toString().trim();

                    // Kontrollera att användaren har fyllt i alla fält
                    if (!originalText.isEmpty() && !keyLengthStr.isEmpty() && !selectedAlgorithm.isEmpty()) {
                        try {
                            int keyLength = Integer.parseInt(keyLengthStr);

                            // Kryptering med AES
                            if (selectedAlgorithm.equalsIgnoreCase("AES")) {
                                aesKey = EncryptionUtils.generateAESKey(keyLength);
                                if (!originalText.equals(cipherText)) {
                                    cipherText = EncryptionUtils.encryptAES(originalText, aesKey);
                                    ciphEdt.setText(cipherText);
                                }
                            }
                            // Kryptering med RSA
                            else if (selectedAlgorithm.equalsIgnoreCase("RSA")) {
                                if (keyLength == 128 || keyLength == 256) {
                                    KeyPair rsaKeyPair = EncryptionUtils.generateRSAKeyPair(keyLength);
                                    publicKey = rsaKeyPair.getPublic();
                                    privateKey = rsaKeyPair.getPrivate();
                                    cipherText = EncryptionUtils.encryptRSA(originalText, publicKey);
                                    ciphEdt.setText(cipherText);
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid RSA Key Length", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid/Incorrect Algorithm selected", Toast.LENGTH_SHORT).show();
                            }
                           // Validation/Error check av inputs från användare
                            tV1.setText("Encrypted Text: " + cipherText);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Invalid Key Length", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Encryption failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Lyssnare för dekrypteringsknapp
            btnDecrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cipherText = ciphEdt.getText().toString().trim();
                    String keyLengthStr = key_dt.getText().toString().trim();
                    String selectedAlgorithm = algorithm.getText().toString().trim();

                    // Kontrollera att användaren har fyllt i alla fält
                    if (!cipherText.isEmpty() && !keyLengthStr.isEmpty() && !selectedAlgorithm.isEmpty()) {
                        try {
                            int keyLength = Integer.parseInt(keyLengthStr);

                            // Avkryptering med AES
                            if (selectedAlgorithm.equalsIgnoreCase("AES")) {
                                String decryptedText = EncryptionUtils.decryptAES(cipherText, aesKey);
                                tV1.setText("Decrypted Text: " + decryptedText);
                            }
                            // Avkryptering med RSA
                            else if (selectedAlgorithm.equalsIgnoreCase("RSA")) {
                                if (keyLength == 128 || keyLength == 256) {
                                    String decryptedText = EncryptionUtils.decryptRSA(cipherText, privateKey);
                                    tV1.setText("Decrypted Text: " + decryptedText);
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid RSA Key Length", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid/Incorrect Algorithm selected", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Invalid Key Length", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Decryption failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// Krpytering appen och koden är skriven av Ali Ferzali för Kursen Tillämpad Programmering på KAU.
