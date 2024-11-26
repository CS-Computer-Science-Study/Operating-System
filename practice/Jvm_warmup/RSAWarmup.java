import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSAWarmup {

    private final String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum accumsan justo non turpis condimentum, nec vulputate erat fringilla. Curabitur vitae mauris eget purus gravida tincidunt.";
    private final String algorithm = "RSA";

    public static void main(String[] args) {
        RSAWarmup rsa = new RSAWarmup();

        printResult(rsa);
        for (int i = 1; i < 200; i++) {
            rsa.method();
        }
        printResult(rsa);
        for (int i = 0; i < 5000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            rsa.method();
        }
        printResult(rsa);
    }

    private static void printResult(RSAWarmup rsa) {
        long startTime = System.nanoTime();
        rsa.method();
        long endTime = System.nanoTime();
        System.out.println("time: " + (endTime - startTime));
    }

    public void method() {
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
