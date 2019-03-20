package com.snsprj.sbsm.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Slf4j
public class EncryptAES {

    private static final String CHAR_ENCODING = "UTF-8";

    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密模式-ECB。ECB模式是分组的模式。
     */
    private static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密模式-CBC。CBC是分块加密后，每块与前一块的加密结果异或后再加密第一块加密的明文是与IV变量进行异或。
     */
    private static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 使用ECB模式进行加密。
     *
     * @param plainText 明文
     * @param key SecretKey
     * @return cipherText
     */
    public static byte[] aesEcbEncode(byte[] plainText, SecretKey key) {

        try {
            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
            | BadPaddingException e) {

            log.error("====>aesEcbEncode error: {}", e);
        }
        return null;
    }

    /**
     * 使用ECB模式进行加密，并返回base64编码后的字符串
     *
     * @param plainText 明文
     * @param key key
     * @return String
     */
    public static String aesEcbEncodeToBase64(String plainText, String key) {

        String base64CipherText = null;

        try {
            SecretKey secretKey = restoreSecretKey(key.getBytes(CHAR_ENCODING));
            base64CipherText = Base64.encodeBase64String(aesEcbEncode(plainText.getBytes(CHAR_ENCODING), secretKey));
        } catch (UnsupportedEncodingException e) {
            log.error("====>aesEcbEncodeToBase64 error: {}", e);
        }
        return base64CipherText;
    }

    /**
     * 使用ECB模式解密
     *
     * @param cipherText 密文
     * @param key SecretKey
     * @return plainText
     */
    public static String aesEcbDecode(byte[] cipherText, SecretKey key) {

        try {
            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
            | BadPaddingException e) {

            log.error("====>aesEcbDecode error: {}", e);
        }
        return null;
    }

    /**
     * 使用ECB模式解密
     *
     * @param cipherText cipherText
     * @param key key
     * @return String
     */
    public static String aesEcbDecodeFromBase64(String cipherText, String key){

        SecretKey secretKey = null;
        try {
            secretKey = restoreSecretKey(key.getBytes(CHAR_ENCODING));
        } catch (UnsupportedEncodingException e) {
            log.error("====>aesEcbDecodeFromBase64 error: {}", e);
        }

        return aesEcbDecode(Base64.decodeBase64(cipherText),secretKey);
    }

    /**
     * CBC模式加密
     *
     * @param plainText 明文
     * @param key SecretKey
     * @param IvParameter initialization vector. CBC模式下：它必须是随机并且保密的，而且它的长度和密码分组相同(比如：对于AES128为128位，即长度为16的byte类型数组)
     * @return cipherText
     */
    public static byte[] aesCbcEncode(byte[] plainText, SecretKey key, byte[] IvParameter) {

        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IvParameter);

            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(plainText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
            | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

            log.error("====>aesCbcEncode error: {}", e);
        }
        return null;
    }

    /**
     * CBC模式解密
     *
     * @param cipherText 密文
     * @param key SecretKey
     * @param IvParameter 与加密模式中使用的值相同
     * @return plainText
     */
    public static String aesCbcDecode(byte[] cipherText, SecretKey key, byte[] IvParameter) {

        IvParameterSpec ivParameterSpec = new IvParameterSpec(IvParameter);

        try {
            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
            | InvalidKeyException | InvalidAlgorithmParameterException
            | IllegalBlockSizeException | BadPaddingException e) {

            log.error("====>aesCbcDecode error: {}", e);
        }

        return null;
    }

    /**
     * 获取SecretKey。 因为某些国家的进口管制限制，Java发布的运行环境包中的加解密有一定的限制。默认不允许256位密钥的AES加解密，解决方法就是修改策略文件。
     *
     * @return byte[] SecretKey
     */
    public static byte[] generateAESSecretKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            // keyGenerator.init(256);
            return keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原密钥
     *
     * @param secretBytes byte[] SecretKey
     * @return SecretKey
     */
    public static SecretKey restoreSecretKey(byte[] secretBytes) {

        SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "AES");
        byte[] enCodeFormat = secretKey.getEncoded();

        return new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
    }
}
