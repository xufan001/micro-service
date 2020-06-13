package com.mw.gateway.utils;

import com.google.common.collect.ImmutableMap;
import com.mw.gateway.entity.User;
import com.mw.gateway.enums.Type;
import com.mw.gateway.exception.BusinessException;
import com.mw.gateway.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserAuth {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;
    /**
     * 校验用户名密码、生成token并返回用户对象
     *
     * @param email
     * @param passwd
     * @return
     */
    public User auth(String email, String passwd) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(passwd)) {
            throw new BusinessException(Type.USER_AUTH_FAIL.name(), "User Auth Fail");
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswd(EncryptUtil.encrypt(passwd));
        user.setEnable(1);
        List<User> list = userService.getUserByQuery(user);
        if (!list.isEmpty()) {
            User retUser = list.get(0);
            onLogin(retUser);
            return retUser;
        }
        throw new BusinessException(Type.USER_AUTH_FAIL.name(), "User Auth Fail");
    }

    private void onLogin(User user) {
        String token = JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(), "ts",
                Instant.now().getEpochSecond() + ""));
        renewToken(token, user.getEmail());
        user.setToken(token);
    }

    private String renewToken(String token, String email) {
        redisTemplate.opsForValue().set(email, token);
        redisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }

    public User getLoginedUserByToken(String token) {
        Map<String, String> map = null;
        try {
            map = JwtHelper.verifyToken(token);
        } catch (Exception e) {
            throw new BusinessException(Type.USER_NOT_LOGIN.name(), "User not login");
        }
        String email = map.get("email");
        Long expired = redisTemplate.getExpire(email);
        if (expired > 0L) {
            renewToken(token, email);
            User user = userService.getUserByEmail(email);
            user.setToken(token);
            return user;
        }
        throw new BusinessException(Type.USER_NOT_LOGIN.name(), "user not login");

    }

    public void invalidate(String token) {
        Map<String, String> map = JwtHelper.verifyToken(token);
        redisTemplate.delete(map.get("email"));
    }


}
