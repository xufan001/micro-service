package com.mw.gateway.enums;

/**
 * 公共Code
 */
public enum Code {

    /**
     * 错误码
     */

    Code_Success("0", "成功"),
    Code_ParamError("8999", "参数错误"),
    Code_Finance_LoanError("6000", "借贷不平"),
    CODE_SERVER_EXCEPTION("1","服务器异常"),
    CODE_PARAM_ERROR("8001", "参数错误"),

    Code_NO_USER_PWD("10001", "用户名或密码错误！"),
    Code_NO_TOKEN_EXPIRED("10004", "登录凭证失效！"),
    Code_Invoke_Server_Exception("11000","调用微服务异常"),
    Code_NO_AUTH_Exception("10005","没有权限使用该功能"),
    Code_TIMEOUT_Exception("10006","服务超时"),

    Code_MQ_KEY_notExist("6001","消息key未构造！"),
    Code_MQ_SEND_DATA_notExist("6002","消息key对应发送数据未构造！"),
    Code_SQL_Exception("10007","delete和update必须带where条件"),
    CODE_RETRY_FAIL("10008", "重试失败"),


    CODE_EXCEL_SHEET_NUM_ERROR("11000", "excel sheet num 超过最大值"),
    CODE_EXCEL_ROW_NUM_ERROR("11001", "excel row num 超过最大值");


    private String name;
    private String value;

    Code(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }


    public String getValue() {
        return value;
    }





}

