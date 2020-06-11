package com.mw.gateway.handle;


import com.mw.gateway.exception.BusinessException;
import com.mw.gateway.token.UserThreadLocal;
import com.mw.gateway.token.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author xufan1
 *  拦截器
 */
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {


    public static final Long DEFAULT_VALUE = 0L;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug(" class:【{}】 pre, request:【{}】 ", this.getClass(), request);


        //处理header参数, 放入threadLocal
        mapHeader(request);

        handleCN(response);


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeLocal();
        //log.info("清理 UserThreadLocal 完成！");
        handleCN(response);


    }

    public void handleExeption(Exception ex){
        if (ex instanceof BusinessException){
            // TODO: 2019/3/19 待处理返回值json标准格式，为兼容过去代码需要判断返回值为string不做处理，如果是指定实体类则处理

        }
    }



    public void mapHeader(HttpServletRequest request){
        log.debug("获取 header ，放入threadLocal");
        String token = getToken(request);
        Long cid = getCid(request);
        Long uid = getUid(request);
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUid(uid);
        userToken.setCid(cid);
        userToken.setUname(getUname(request));
        userToken.setCname(getCname(request));
        userToken.setSysId(getSysId(request));
        userToken.setStaffId(getStaffId(request));
        UserThreadLocal.setLocal(userToken);
    }


    public String getToken(HttpServletRequest request){
        String token =  request.getHeader("Authorization");
        //log.info("token : {}", token);
        return token;
    }

    public Long getUid(HttpServletRequest request){
        String uid =  request.getHeader("uid");
        if (StringUtils.isEmpty(uid)){
            return DEFAULT_VALUE;
        }
        return Long.valueOf(uid);
    }

    /**
     * 从header获取公司ID
     * @param request
     * @return
     */
    public Long getCid(HttpServletRequest request){
        String cid =  request.getHeader("cid");
        if (StringUtils.isEmpty(cid)){
            return DEFAULT_VALUE;
        }
        return Long.valueOf(cid);
    }

    public String getUname(HttpServletRequest request){
        String uname =  request.getHeader("uname");
        return uname;
    }

    public String getCname(HttpServletRequest request){
        String cname =  request.getHeader("cname");
        if (StringUtils.isNotEmpty(cname)){
            try {
                cname = URLDecoder.decode(cname, "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("getCname decode error!", e);
            }
        }
        return cname;
    }

    public Long getSysId(HttpServletRequest request){
        String sysId =  request.getHeader("sysId");
        if (StringUtils.isEmpty(sysId)){
            return DEFAULT_VALUE;
        }
        return Long.valueOf(sysId);
    }

    public Long getStaffId(HttpServletRequest request){
        String staffId =  request.getHeader("staffId");
        if (StringUtils.isBlank(staffId)||staffId.equals("null")){
            return DEFAULT_VALUE;
        }
        return Long.valueOf(staffId);
    }

    public void handleCN(HttpServletResponse  response){
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
}

