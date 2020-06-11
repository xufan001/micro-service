package com.mw.gateway.api;


import com.mw.gateway.enums.Code;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;

@Data
public class ApiResult<T> implements Serializable {

    private String code;
    private String msg;
    private T data;

    private static class ApiResultHolder{
        private static ApiResult apiResult = new ApiResult();
    }



    public static ApiResult build(String code, String msg, Object data){
        ApiResult apiResult =  new ApiResult();
        apiResult.code = code;
        apiResult.msg = msg;
        data = data == null ? Collections.emptyList() : data;
        apiResult.data = data;
        return apiResult;
    }
    public static ApiResult buildForServe(String code, String msg, Object data){
        ApiResult apiResult =  new ApiResult();
        apiResult.code = code;
        apiResult.msg = msg;
        apiResult.data = data;
        return apiResult;
    }


    public static ApiResult build(String code, String msg){
        return buildForServe(code, msg, null);
    }


    /**
     * 微服务调用失败，返回公共code
     * @return
     */
    public static ApiResult getApiFackBack(){
        Code code = Code.Code_Invoke_Server_Exception;
        return ApiResult.build(code.getName(), code.getValue());
    }

    public static ApiResult getApiFackBack(String serviceName){
        Code code = Code.Code_Invoke_Server_Exception;
        return ApiResult.build(code.getName(), code.getValue() + ":" + serviceName);
    }

}
