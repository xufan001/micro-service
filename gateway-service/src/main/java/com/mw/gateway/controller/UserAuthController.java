package com.mw.gateway.controller;

import com.mw.gateway.api.ApiResult;
import com.mw.gateway.entity.User;
import com.mw.gateway.enums.Code;
import com.mw.gateway.utils.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userAuth")
public class UserAuthController {


    @Autowired
    private UserAuth userAuth;

    @RequestMapping("auth")
    public ApiResult<User> auth(@RequestBody User user) {
        User finalUser = userAuth.auth(user.getEmail(), user.getPasswd());
        return ApiResult.build(Code.Code_Success.getName(),Code.Code_Success.getValue(),finalUser);
    }


    @RequestMapping("get")
    public ApiResult<User> getUser(String token) {
        User finalUser = userAuth.getLoginedUserByToken(token);
        return ApiResult.build(Code.Code_Success.getName(),Code.Code_Success.getValue(),finalUser);
    }

    @RequestMapping("logout")
    public ApiResult logout(String token) {
        userAuth.invalidate(token);
        return ApiResult.build(Code.Code_Success.getName(),Code.Code_Success.getValue());
    }

    /***
     * 步骤：
     * 1.发送带有用户名和密码的post请求
     * 2.验证通过后生成jwt并将用户信息放入jwt
     * 3.将jwt存入cookie中
     * 4.发送带有jwt cookie的请求
     * 5.验证签名后从jwt中获取用户信息
     * 6.返回响应信息
     */

}
