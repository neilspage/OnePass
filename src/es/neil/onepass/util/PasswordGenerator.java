package es.neil.onepass.util;

import sun.misc.BASE64Encoder;

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
            password += encryptAES(serviceName, masterPass);
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

    static String generateStringOnSeed(String seed)
    {
        int stringSize = 16;
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        StringBuffer r = new StringBuffer();

        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (stringSize > 0)) {

                r.append(ch);
                stringSize--;
            }
        }

        return r.toString();
    }
}
