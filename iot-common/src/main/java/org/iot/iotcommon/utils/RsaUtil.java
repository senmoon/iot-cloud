package org.iot.iotcommon.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtil {
    public static String PublicKeyPrefix = "publicKey-";
    public static String PrivateKeyPrefix = "privateKey-";
    private static Map<String, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥

    public static Map<String, String> getRsaMap(int keySize, String algorithm, String account) {
        if (account == null || "".equals(account)) return null;
        if (keyMap.get(PublicKeyPrefix + account) != null) {
            return keyMap;
        }
        if (keySize < 0) keySize = 1024;
        if (algorithm == null) algorithm = "RSA";
        //生成公钥和私钥
        try {
            keyMap = genKeyPair(keySize, algorithm, account);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyMap;
    }

    /**
     * 随机生成密钥对
     */
    private static Map<String, String> genKeyPair(int keySize, String algorithm, String account) throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(keySize, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(PublicKeyPrefix + account, publicKeyString);  //公钥
        keyMap.put(PrivateKeyPrefix + account, privateKeyString);  //私钥
        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

//    public static void main(String[] args) throws Exception {
//        genKeyPair();
//        //加密字符串
//        String message = "df723820";
//        System.out.println("随机生成的公钥为:" + keyMap.get("publicKey"));
//        System.out.println("随机生成的私钥为:" + keyMap.get("privateKey"));
//        String messageEn = encrypt(message,keyMap.get("publicKey"));
//        System.out.println(message + "，加密后的字符串为:" + messageEn);
//        String messageDe = decrypt(messageEn,keyMap.get("privateKey"));
//        System.out.println("还原后的字符串为:" + messageDe);
//    }
}
