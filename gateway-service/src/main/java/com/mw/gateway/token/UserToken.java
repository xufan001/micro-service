package com.mw.gateway.token;
import lombok.Data;

@Data
public class UserToken {

    private String token;

    /**
     * 用户账户ID
     */
    private Long uid;

    /**
     * 公司ID
     */
    private Long cid;

    /**
     * 用户名
     */
    private String uname;


    /**
     * 公司名称
     */
    private String cname;

    /**
     * 系统ID
     */
    private Long sysId;

    /**
     * 员工ID
     */
    private Long staffId;


    /**
     * 用于标识消息的ID
     */
    private String mqId;

}

