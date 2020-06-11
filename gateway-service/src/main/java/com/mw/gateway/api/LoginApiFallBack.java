package com.mw.gateway.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginApiFallBack implements LoginApi {


    @Override
    public UserTokenDto loginRefresh(String refresh_token, String grant_type) {
        return null;
    }
}
