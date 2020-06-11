package com.mw.gateway.aop;


import com.alibaba.fastjson.JSONObject;
import com.mw.gateway.api.LoginApi;
import com.mw.gateway.api.UserTokenDto;
import com.mw.gateway.enums.Code;
import com.mw.gateway.token.RefreshToken;
import com.mw.gateway.token.UserThreadLocal;

import com.mw.gateway.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;


import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * aop refresh token
 */
@Component
@Aspect
public class UserTokenAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoginApi loginApi;

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.mw.gateway.token.RefreshToken) ")
    public void annotationPoint() {

    }

    @Around("annotationPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try {
            //执行接口调用
            logger.info("调用接口！");
            result = point.proceed();
            //处理逻辑
            boolean finished = handle(point, result);
            logger.info("刷新token后，是否重新执行成功：{}", finished);
        } catch (Exception e) {
            logger.error("RefreshToken around annotationPoint() handel error", e);
        }
        return result;
    }


    /**
     * 处理逻辑
     * @param point
     * @param result
     */
    public boolean handle(ProceedingJoinPoint point, Object result) throws Throwable {
        //是否需要刷新，如果code 不是过期，则不需要刷新token
        if (!isNeedRefreshToken(result)){
            return false;
        }
        //反射获取需要的刷新的类信息
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        // 获取方法额输入参数
        Object[] arguments = point.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Method curMethod = isSetRefresh(methods, methodName, arguments);

        //如果没设置RefreshToken 则不需要刷新token
        if (curMethod == null){
            return false;
        }
        if (result != null){
            //String oldToken = UserThreadLocal.getToken();
            String token = getNewToken();
            if (StringUtils.isNotEmpty(token)){
                UserThreadLocal.getLocal().setToken(token);
            }
            // 默认获取第一个参数，认为是token, String类型
//            Object paramEntity = arguments[0];
//            if (paramEntity instanceof String){
//                oldToken = (String)paramEntity;
//                //获取新token
//                token = getNewToken(oldToken);
//                //新token赋值回参数
//                arguments[0] = token;
//            }else{
//                Method tokenMethod = ReflectionUtils.findMethod(paramEntity.getClass(), "getToken");
//                Method tokenSetMethod = ReflectionUtils.findMethod(paramEntity.getClass(), "setToken", String.class);
//                oldToken = (String) ReflectionUtils.invokeMethod(tokenMethod, paramEntity);
//                //获取新token
//                token = getNewToken(oldToken);
//                //新token赋值回参数
//                tokenSetMethod.invoke(paramEntity, token);
//                arguments[0] = paramEntity;
//
//            }
//            logger.info("oldToken : {} ", oldToken);
//            logger.info("newToken : {} ", token);
//            logger.info("刷新token后，重新执行方法！");
            point.proceed(arguments);

            return true;
        }
        return false;
    }

    public Method isSetRefresh(Method[] methods, String methodName, Object[] arguments){
        Method curMethod = null;
        RefreshToken refreshToken = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    curMethod = method;
                    refreshToken = method.getAnnotation(RefreshToken.class);
                    break;
                }
            }
        }
        if (curMethod == null || refreshToken == null){
            return null;
        }
        return curMethod;
    }

    /**
     * 获取新token
     * @return
     */
    public String getNewToken(){
        //通过userName 从redis获取 refreshToken
        String userName = UserThreadLocal.getUname();
        logger.info("userName: {}", userName);
        String refreshTokenStr = (String)redisTemplate.opsForValue().get("refreshToken:" + userName);
        logger.info("从redis 获取refreshToken: 【{}】", refreshTokenStr);
        UserTokenDto userTokenDto = loginApi.loginRefresh(refreshTokenStr, "refresh_token");
        logger.info("调用认证中心的刷新token接口重新获取token...");
        if (Code.Code_Success.getName().equals(userTokenDto.getCode())){
            //获取新的token
            String token = userTokenDto.getData().getToken();
            logger.info("调用认证中心的刷新token接口重新获取token:【{}】", token);
            if (StringUtils.isNotEmpty(token)){
                logger.info("开始加入newToken的前缀", token);
                token = "Bearer " + token;
            }
            return token;
        }
        return null;
    }

    /**
     * 判断是否需要刷新
     * @param result
     * @return
     */
    public boolean isNeedRefreshToken(Object result) throws InvocationTargetException, IllegalAccessException {
        String code = null;
        if (result instanceof String){
            logger.info("返回值为String");
            String jsonResult = (String)result;
            JSONObject jsonObject = JsonUtils.jsonStr2Obj(jsonResult);
            code = (String)jsonObject.get("code");
            logger.info("code : {}", code);
        }else {
            Method codeMethod = ReflectionUtils.findMethod(result.getClass(), "getCode");
            if (codeMethod != null) {
                code = (String) codeMethod.invoke(result);
            }
            logger.info("返回值为其他实体类");
        }
        if (Code.Code_NO_TOKEN_EXPIRED.getName().equals(code)){
            return true;
        }
        return false;
    }

}
