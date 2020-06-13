package com.mw.gateway.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xufan1
 * 用户表
 */
@Data
public class User extends BaseBean implements Serializable {


    private String email;

    private String passwd;

    private Integer enable;

    private String name;

    private String token;

}
