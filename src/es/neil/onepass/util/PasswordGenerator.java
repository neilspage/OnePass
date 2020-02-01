package es.neil.onepass.util;

import sun.misc.BASE64Encoder;
import sun.rmi.runtime.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Arrays;
import java.util.Random;

public class PasswordGenerator {
    private static final String ENCRYPTION_ALGORITHM = "AES";

    public static String generatePass(String masterPass, String serviceName, int desiredPassLength) throws Exception {
        String password = "";

        while (password.length() < desiredPassLength) {
            password += encryptAES(serviceName, ensureSaltIs16Bits(masterPass));
        }

        return password;
    }

    private static String encryptAES(String toEncrypt, String encryptionSalt) throws Exception{
        Key keySpec = new SecretKeySpec(convertStringToByteArray(encryptionSalt), ENCRYPTION_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encValue = cipher.doFinal(toEncrypt.getBytes());
        String stringEncrypted = new BASE64Encoder().encode(encValue);
        return stringEncrypted;
    }

    private static byte[] convertStringToByteArray(String inputString){
        CharBuffer charBuffer = CharBuffer.wrap(inputString.toCharArray());
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

    //Generate filler if masterPass (salt) is less than 16 bits.
    public static String ensureSaltIs16Bits(String salt) {
        int saltLength = salt.length();
        if(saltLength < 16){
            int difference = 16 - salt.length();
            for(int i = 0; i < difference; i++){
                salt+="-";
            }
        }

        if(saltLength > 16){
            int difference = salt.length() - 16;
            salt = salt.substring(0, saltLength-difference);
        }
        System.out.println(salt);
        return salt;
    }
}
