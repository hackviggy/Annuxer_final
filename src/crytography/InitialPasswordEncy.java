
package crytography;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import static javax.crypto.Cipher.*;

public class InitialPasswordEncy<bytes> {

    private SecretKey secKey;
    private Cipher aesCipher;
    byte[] byteCipherText;

    public String encrypt(String initialPwd) {
        try {
            System.out.println("inital pwd " + initialPwd);
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128); // The AES key size in number of bits
            secKey = generator.generateKey();
            aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
            byteCipherText = aesCipher.doFinal(initialPwd.getBytes());
            System.out.println("cipher text " + new String(byteCipherText));
            return new String(byteCipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String initlaPwd) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair keyPair = kpg.generateKeyPair();
            PublicKey puKey = keyPair.getPublic();
            PrivateKey prKey = keyPair.getPrivate();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.PUBLIC_KEY, puKey);
            byte[] encryptedKey = cipher.doFinal(secKey.getEncoded());
            System.out.println("ency key " + new String(encryptedKey));
            cipher.init(Cipher.PRIVATE_KEY, prKey);
            byte[] decryptedKey = cipher.doFinal(encryptedKey);
            //Convert bytes to AES SecertKey
            System.out.println("decy key " + new String(decryptedKey));
            SecretKey originalKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
            aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            System.out.println("plan text " + new String(bytePlainText));
            return new String(bytePlainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

