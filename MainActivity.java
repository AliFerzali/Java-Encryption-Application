package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

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

        inputMessage = findViewById(R.id.inputMessage);
        ciphEdt = findViewById(R.id.ciphEdt);
        key_dt = findViewById(R.id.key_dt);
        algorithm = findViewById(R.id.algorithm);
        btnEncrypt = findViewById(R.id.btnencrypt);
        btnDecrypt = findViewById(R.id.btndecrypt);
        tV1 = findViewById(R.id.tV1);

        try {
            btnEncrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String originalText = inputMessage.getText().toString().trim();
                    String cipherText = ciphEdt.getText().toString().trim();
                    String keyLength = key_dt.getText().toString().trim();
                    String selectedAlgorithm = algorithm.getText().toString().trim();

                    if (!originalText.isEmpty() && !keyLength.isEmpty() && !selectedAlgorithm.isEmpty()) {
                        try {
                            if (selectedAlgorithm.equalsIgnoreCase("AES")) {
                                // Generera AES-nyckel
                                aesKey = EncryptionUtils.generateAESKey(Integer.parseInt(keyLength));

                                // Kryptera med AES
                                String encryptedText = EncryptionUtils.encryptAES(originalText, aesKey);
                                tV1.setText(encryptedText);
                            } else if (selectedAlgorithm.equalsIgnoreCase("RSA")) {
                                // Generera RSA-nyckelpar
                                KeyPair rsaKeyPair = EncryptionUtils.generateRSAKeyPair(Integer.parseInt(keyLength));
                                publicKey = rsaKeyPair.getPublic();
                                privateKey = rsaKeyPair.getPrivate();

                                // Kryptera med RSA
                                String encryptedText = EncryptionUtils.encryptRSA(originalText, publicKey);
                                tV1.setText(encryptedText);
                            } else {
                                Toast.makeText(MainActivity.this, "Ogiltig/fel algoritm har valts", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Fyll i alla fälten", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnDecrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String originalText = inputMessage.getText().toString().trim();
                    String cipherText = ciphEdt.getText().toString().trim();
                    String keyLength = key_dt.getText().toString().trim();
                    String selectedAlgorithm = algorithm.getText().toString().trim();

                    if (!cipherText.isEmpty() && !keyLength.isEmpty() && !selectedAlgorithm.isEmpty()) {
                        try {
                            if (selectedAlgorithm.equalsIgnoreCase("AES")) {
                                // Generera AES-nyckel
                                aesKey = EncryptionUtils.generateAESKey(Integer.parseInt(keyLength));

                                // Dekryptera med AES
                                String decryptedText = EncryptionUtils.decryptAES(cipherText, aesKey);
                                tV1.setText(decryptedText);
                            } else if (selectedAlgorithm.equalsIgnoreCase("RSA")) {
                                // Dekryptera med RSA
                                String decryptedText = EncryptionUtils.decryptRSA(cipherText, privateKey);
                                tV1.setText(decryptedText);
                            } else {
                                Toast.makeText(MainActivity.this, "Ogiltig/fel algoritm har valts", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Fyll i alla fälten", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
