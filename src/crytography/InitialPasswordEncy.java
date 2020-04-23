
package crytography;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class InitialPasswordEncy {
    public String encrypt(String initialPassword) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //Initializing a Cipher object
            cipher.init(ENCRYPT_MODE, pair.getPublic());
            cipher.update(initialPassword.getBytes());
            String ency = new String(cipher.doFinal(), StandardCharsets.UTF_8);
            System.out.println("initial password ency " + ency);
            return ency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String initialPassword) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(DECRYPT_MODE, pair.getPrivate());
            String ecy = new String(cipher.doFinal(initialPassword.getBytes()));
            System.out.println(ecy);
            return ecy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
