package com.mw.gateway.utils;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mw.gateway.api.ApiResult;
import com.mw.gateway.enums.Code;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 内部json工具类
 */
@Slf4j
public class JsonUtils {

    public static JSONObject jsonStr2Obj(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject;
    }

    public static JSONObject getData(String json){
        JSONObject jsonObject = jsonStr2Obj(json);
        jsonObject = jsonObject.getJSONObject("data");
        return jsonObject;
    }

    public static ApiResult getCodeAndMsg(String json){
        JSONObject jsonObject = jsonStr2Obj(json);
        String code = (String)jsonObject.get("code");
        String msg = (String)jsonObject.get("msg");
        return ApiResult.build(code, msg);
    }

    public static final <T> T parseObject(String json, TypeReference<T> type) {
        return (T) JSONObject.parseObject(json, type.getType());
    }


    public static String getCode(String json){
        JSONObject jsonObject = jsonStr2Obj(json);
        String code = (String)jsonObject.get("code");
        return code;
    }

    public static String getMsg(String json){
        JSONObject jsonObject = jsonStr2Obj(json);
        String msg = (String)jsonObject.get("msg");
        return msg;
    }
    public static String jsonMessage(Code code, Object object) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", code.getName());
        returnMap.put("msg", code.getValue());
        returnMap.put("data", object);
        log.info("请求结果: {}", JSON.toJSONString(returnMap, SerializerFeature.WriteMapNullValue));
        return JSON.toJSONString(returnMap, SerializerFeature.WriteMapNullValue);
    }


    public static String jsonMessage(String code, String msg, Object object) {
        Map<String, Object> returnMap = new HashMap();
        returnMap.put("code", code);
        returnMap.put("msg", msg);
        returnMap.put("data", object);
        log.info("请求结果: {}", JSON.toJSONString(returnMap, new SerializerFeature[]{SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect}));
        return JSON.toJSONString(returnMap, new SerializerFeature[]{SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect});
    }

    public static String jsonMessage(String code, String msg) {
        return jsonMessage(code, msg, null);
    }


}
