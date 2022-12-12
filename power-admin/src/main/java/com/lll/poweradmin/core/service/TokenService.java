package com.lll.poweradmin.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lll.poweradmin.common.constant.Constants;
import com.lll.poweradmin.model.domain.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {

    private  final String SECRET = "!34ADAS";

    private  final long expireTime = 0;


    public String createToken(User user){
        String uuid = UUID.randomUUID().toString();
        HashMap<String, String> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_ID,uuid);
        return createToken(claims);
    }

    public  String createToken(Map<String,String> claims){
        // 指定token过期时间为10秒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return JWT.create()

                .withExpiresAt(calendar.getTime())  // 过期时间
                .sign(Algorithm.HMAC384(SECRET));
    }

    public String parseToken(String token){
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC384(SECRET)).build();
        // 解析指定的token
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        // 获取解析后的token中的payload信息
        Claim userId = decodedJWT.getClaim("userId");
        Claim userName = decodedJWT.getClaim("userName");
        System.out.println(userId.asInt());
        System.out.println(userName.asString());
        // 输出超时时间
        System.out.println(decodedJWT.getExpiresAt());
        return "";
    }




}
