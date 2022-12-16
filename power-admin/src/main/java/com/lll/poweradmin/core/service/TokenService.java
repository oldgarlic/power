package com.lll.poweradmin.core.service;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lll.poweradmin.common.constant.Constants;
import com.lll.poweradmin.core.redis.RedisCache;
import com.lll.poweradmin.model.dto.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String SECRET ;

    @Value("${token.expire}")
    private int expireTime ;

    @Value("${token.header}")
    private String header;

    @Resource
    private RedisCache redisCache;

    private static final long MILLISECOND_MINUTE = 1000*60;
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    public void deleteLoginUser(String loginUserId){
        String tokenRedisKey = getTokenRedisKey(loginUserId);
        redisCache.delStringCache(tokenRedisKey);
    }

    public String createToken(LoginUser loginUser){
        String loginUserId = UUID.randomUUID().toString();
        loginUser.setLoginUserId(loginUserId);
        // 获取请求的ip地址,根据ip查询出来的登录地址，客户端，浏览器
        // 用户信息记录Redis
        setUserAgent(loginUser);
        refreshToken(loginUser);

        HashMap<String, String> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_ID,loginUserId);
        return createToken(claims);
    }

    public void setUserAgent(LoginUser loginUser){
        // TODO: 解析出用户登录信息

    }

    public void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime()+expireTime*MILLISECOND_MINUTE);
        String tokenRedisKey = getTokenRedisKey(loginUser.getLoginUserId());
        redisCache.setStringCache(tokenRedisKey,loginUser,expireTime, TimeUnit.MINUTES);
    }

    public  String createToken(Map<String,String> claims){
        // 指定token过期时间为10秒
        JWTCreator.Builder builder = JWT.create();
        claims.forEach(builder::withClaim);
        return builder.sign(Algorithm.HMAC384(SECRET));
    }

    public String parseToken(String token){
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC384(SECRET)).build();
        // 解析指定的token
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        // 获取解析后的token中的payload信息
        Claim claim = decodedJWT.getClaim(Constants.LOGIN_USER_ID);
        return claim.asString();
    }

    public String getTokenRedisKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY+uuid;
    }

    private String getToken(HttpServletRequest request){
        String token = request.getHeader(this.header);
        if(StringUtils.isNotBlank(token) && token.startsWith(Constants.TOKEN_PREFIX)){
            token = token.replace(Constants.TOKEN_PREFIX,"");
        }
        return token;
    }

    public LoginUser getLoginUser(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isNotEmpty(token)){
            String uuid = parseToken(token);
            String tokenRedisKey = getTokenRedisKey(uuid);
            String loginUserStr = redisCache.getStringCache(tokenRedisKey);
            return JSON.parseObject(loginUserStr, LoginUser.class);
        }
        return null;
    }

    public void verifyToken(LoginUser loginUser){
        Long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if(expireTime - currentTime <MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
        }
    }

}
