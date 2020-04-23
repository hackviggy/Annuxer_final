package crytography;

import utils.CommonUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class PhasePasswordEncy {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            setKey(CommonUtils.ENCY_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, secretKey);
            String phasePass = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
            System.out.println("phase password ency =" + phasePass);
            return phasePass;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            setKey(CommonUtils.ENCY_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(DECRYPT_MODE, secretKey);
            String decy = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            System.out.println("phase password decy =" + decy);
            return decy;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
