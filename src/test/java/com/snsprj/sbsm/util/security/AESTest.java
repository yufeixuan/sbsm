package com.snsprj.sbsm.util.security;

import com.snsprj.sbsm.utils.security.EncryptAES;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

@Slf4j
public class AESTest {

    @Test
    public void test() {

        // 原文
        String plainText = "反三俗";

        // 生成私钥
        byte[] secretBytes = EncryptAES.generateAESSecretKey();
        SecretKey secretKey = EncryptAES.restoreSecretKey(secretBytes);

//        // ECB模式加密
//        byte[] encodedText = EncryptAES.aesEcbEncode(plainText.getBytes(), secretKey);
//
//        System.out.println("AES ECB encoded with Base64: " + Base64.encodeBase64String(encodedText));
//        System.out.println("AES ECB decoded: " + EncryptAES.aesEcbDecode(encodedText, secretKey));
//
//        // 获取向量数组
//        byte[] IvParameters = EncryptAES.generateAESSecretKey();
//
//        // CBC模式加密
//        encodedText = EncryptAES.aesCbcEncode(plainText.getBytes(), secretKey, IvParameters);
//        System.out.println(encodedText);
//
//        System.out.println("AES CBC encoded with Base64: " + Base64.encodeBase64String(encodedText));
//        System.out.println("AES CBC decoded: " + EncryptAES.aesCbcDecode(encodedText, secretKey, IvParameters));
//
        String key = String.valueOf(secretBytes);

        key = "1234567890abcdef";
        String cipherText = EncryptAES.aesEcbEncodeToBase64(plainText, key);
        log.info("===>cipherText is {}", cipherText);

        log.info("====>plainText is {}", EncryptAES.aesEcbDecodeFromBase64(cipherText, key));

        log.info("============");
    }
}
