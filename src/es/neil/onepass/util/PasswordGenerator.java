package es.neil.onepass.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordGenerator {
    public static String computePass(String masterpass, String service) throws Exception {
        String concatArgs = masterpass+"!--+--!"+service; //concatonate the two arguments, and then MD5 hash them.
        return new String(encryptRc4(service+service+service+service+service+service+service+service+service+service+service+service+service,concatArgs+concatArgs+concatArgs+concatArgs+concatArgs+concatArgs+concatArgs+concatArgs+concatArgs),"US-ASCII").replaceAll("\\W", "");
    }

    private static byte[] encryptRc4(String toEncrypt, String key) throws Exception{
        SecureRandom sr = new SecureRandom(key.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(sr);
        SecretKey sk = kg.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return cipher.doFinal(toEncrypt.getBytes());
    }
}
