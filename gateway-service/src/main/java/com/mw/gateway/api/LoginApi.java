package com.mw.gateway.api;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="gateway-server", url = "${feign.urls.gateway-server:}", fallback = LoginApiFallBack.class)
public interface LoginApi {

    @PostMapping("/login")
    UserTokenDto loginRefresh(@RequestParam(value = "refresh_token", required = false) String refresh_token,
                              @RequestParam(value = "grant_type", required = false) String grant_type);


}
