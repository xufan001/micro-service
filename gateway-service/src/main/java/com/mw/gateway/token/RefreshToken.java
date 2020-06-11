package com.mw.gateway.token;

import java.lang.annotation.*;

/**
 * 用户refresh_token
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshToken {

    String remark() default "";
}

