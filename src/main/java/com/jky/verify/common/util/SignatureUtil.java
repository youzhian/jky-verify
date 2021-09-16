package com.jky.verify.common.util;

import com.jky.verify.modules.common.bean.SignInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 签名和验签工具类
 * @author youzhian
 */
public class SignatureUtil {
    /**
     * 签名
     * @param params
     * @param signKey
     * @return
     */
    public static String sign(Map<String, String> params, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(signKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 校验签名
     * @param params
     * @param privateKey
     * @return
     */
    public static Boolean verify(Map<String, String> params,  String privateKey) {
        String sign = sign(params, privateKey);
        return sign.equals(params.get("sign"));
    }

    public static void main(String[]args){
        Map<String,String> params = new HashMap<>();
        String signKey = "JBmozbVXZCKjbqX52Nmmw";
        String appId = "wfdcIbshtHJb1E6hawo";
        String nonce = "1232131afdssda";
        String timestamp = "20201130120412";
        String name = "";
        String id = "12";
        SignInfo s = new SignInfo();
        s.setAppId("appid1123");
        s.setNonce("nonce444");
        s.setSign("sign12323");
        params.put(Constant.APPID_KEY,appId);
        params.put(Constant.NONCE_KEY,nonce);
        params.put(Constant.TIMESTAMP_KEY,timestamp);
        params.put("signInfo",String.valueOf(s));
        //params.put("id",id);
        String sign = sign(params,signKey);
        System.out.println(sign);
    }
}
