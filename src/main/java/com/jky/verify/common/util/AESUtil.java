package com.jky.verify.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密/解密工具类
 * @author youzhian
 */
public class AESUtil {
    /**
     * 加密的算法、工作模式和填充方式
     */
    private static String model = "AES/ECB/PKCS5Padding";
    /**
     * random编码
     */
    private static String randomModel = "SHA1PRNG";
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 根据秘钥与内容进行AES加密
     * @param secret 秘钥
     * @param content 要加密的内容
     * @return 加密的结果
     */
    public static String encryption(String secret,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom sr = SecureRandom.getInstance(randomModel);
            sr.setSeed(secret.getBytes());
            keygen.init(128, sr);
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            String AES_encode=new String(Base64.getEncoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    /**
     * 根据秘钥对加密的数据进行解密
     * @param secret 秘钥
     * @param content 加密后的内容
     * @return 解密的结果
     */
    public static String decrypt(String secret,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据secret规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom sr = SecureRandom.getInstance(randomModel);
            sr.setSeed(secret.getBytes());
            keygen.init(128, sr);
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= Base64.getDecoder().decode(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static void main(String []args){
        /*String key = "1455c2b76530b0e0d939a39bf85279b6fa0f324d33eb5d802bcf95b7e65d40a5";
        String content = "10086110911";
        String enContent = encryption(key,content);
        System.out.println("["+content+"]加密后的内容是：["+enContent+"]");
        String deContent = decrypt(key,enContent);
        System.out.println("["+enContent+"]解密后的内容是["+deContent+"]");
        System.out.println(AesUtils.aesEncrypt(content,key,"AES/ECB/PKCS5Padding"));*/
        String key = "wfdcIbshtHJb1E6hawo";
        String deContent = "TjZ36YGNRSwIJiLYog6vaT8ScC6irvD4nTwD1vdC9RQ=";
        System.out.println(decrypt(key,deContent));
        System.out.println(encryption("JBmozbVXZCKjbqX52Nmmw",key));
    }
}
