package com.mw.account.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xufan1
 * 用户个人信息控制层
 */
@RestController
@RequestMapping(value = "/account/userInfo")
public class UserInfoController {



    @GetMapping(value = "/info")
    public String info() {
        return "欢迎来到账户服务";
    }


}
