package com.mw.gateway.api;

import lombok.Data;

@Data
public class TokenRefreshDto {

    private String refresh_token;

    private String grant_type;


}
