package com.mw.gateway.api;

import lombok.Data;

@Data
public class UserTokenBo {

    private String token;

    private String refreshToken;


}
