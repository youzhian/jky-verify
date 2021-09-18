package com.jky.verify.common.util.jackyun;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jky.verify.common.util.Md5Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt token 生成和解析
 *
 * @author yongfei.zheng
 * @date 2020/7/24 17:51.
 */
public class JwtUtil {

    /**
     * claim key
     */
    private static final String STRING_KEY = "token";

    /**
     * 过期时间,毫秒
     */
    private static final long EXPIRE_TIME = 300000L;

    /**
     * 生成token的 header
     */
    private static final Map<String, Object> JWT_HEADER = new HashMap<>(2);


    private JwtUtil() {
        JWT_HEADER.put("Type", "Jwt");
        JWT_HEADER.put("alg", "HS256");
    }

    /**
     * 生成token
     *
     * @param token  Token对象
     * @param secret 密钥
     * @return
     */
    public static String createClaimsJwt(Token token, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withHeader(JWT_HEADER)
                .withClaim(STRING_KEY, JSON.toJSONString(token))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(algorithm);
    }

    /**
     * 解析jwt token, 得到Token对象
     *
     * @param token  jwt token字符串
     * @param secret 密钥
     * @return
     */
    public static Token parseClaimsJwt(String token, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT jwt = verifier.verify(token);

        Claim claim = jwt.getClaim(STRING_KEY);
        if (claim == null) {
            return null;
        }

        return JSON.parseObject(claim.asString(), Token.class);
    }

    public static void main(String[] args){
        Token token = new Token();
        token.setAccount("zhangsan");
        token.setUserId("123");
        token.setUserName("张三");
        token.setAppId("7251790331105755");
        token.setPlatformName("核放出入区");
        String jwtToken = createClaimsJwt(token,"x2lhqC8pUoLdXkpJ2wHIRc2WeLrQssd7");
        System.out.println(jwtToken);
        System.out.println(Md5Util.md5("jky456123"));
    }
}
