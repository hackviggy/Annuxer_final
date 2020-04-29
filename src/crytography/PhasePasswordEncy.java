package crytography;

import utils.CommonUtils;
import utils.Output;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class PhasePasswordEncy {

    private static SecretKeySpec secKey;
    private static byte[] key;

    private static void setKey(String myKey) {
        MessageDigest sha;
        Output out=new Output();
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secKey = new SecretKeySpec(key, "AES");
            System.out.println( "secret key "+new String(secKey.getEncoded()));
            System.out.println(" secret key length "+secKey.toString().length());
            System.out.println(" secret key size "+out.getMemSize(secKey.toString().getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            setKey(CommonUtils.ENCY_KEY);
            Output out=new Output();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, secKey);
            long s=System.nanoTime();
            String phasePass = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
            long e=System.nanoTime();
            float timeSec = e-s;
            System.out.println("Phase key "+phasePass);
            System.out.println(" phase key length "+phasePass.toString().length());
            System.out.println(" phase key size "+out.getMemSize(secKey.toString().getBytes()));
            System.out.printf("Phase encryt time "+String.format("%.3f", (timeSec/1000000))+"\n");
            return phasePass;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            Output out=new Output();
            setKey(CommonUtils.ENCY_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(DECRYPT_MODE, secKey);
            long s=System.nanoTime();
            String decy = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            long e=System.nanoTime();
            float timeSec = e-s;
           // System.out.println("phase password decy =" + decy);
            System.out.printf("Phase decryt time "+String.format("%.3f", (timeSec/1000000))+"\n");
            return decy;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
