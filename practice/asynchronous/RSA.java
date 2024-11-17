package asynchronous;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSA implements Runnable {

    private final String message;
    private final String algorithm = "RSA";

    public RSA(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            KeyPair keyPair = generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            String encryptedMessage = encrypt(message, publicKey);
            //System.out.println("Encrypted Message: " + encryptedMessage);

            String decryptedMessage = decrypt(encryptedMessage, privateKey);
            //System.out.println("Decrypted Message: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    private String encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String decrypt(String encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }
}

