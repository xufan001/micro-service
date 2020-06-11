package com.mw.gateway.handle;

import com.mw.gateway.token.UserThreadLocal;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName FeignHeaderInterceptor
 * @Description api调用设置header
 * @Author ptw
 * @Date 2019/3/20 上午11:52
 * @Version 1.0
 **/
@Slf4j
@Component
public class FeignHeaderInterceptor implements RequestInterceptor {

//    @Override
//    public void apply(RequestTemplate template) {
//
//    }

    @Override
    public void apply(feign.RequestTemplate template) {
        template.header(HttpHeaders.AUTHORIZATION, UserThreadLocal.getToken());
        Long cid = UserThreadLocal.getCid();
        Long uid = UserThreadLocal.getUid();
        Long sysId = UserThreadLocal.getSysId();
        Long staffId = UserThreadLocal.getStaffId();
        if (cid != null){
            template.header("cid", UserThreadLocal.getCid().toString());
        }
        if (uid != null){
            template.header("uid", UserThreadLocal.getUid().toString());
        }
        if (sysId != null){
            template.header("sysId", String.valueOf(sysId));
        }
        if (staffId != null){
            template.header("staffId", String.valueOf(staffId));
        }
        template.header("uname", UserThreadLocal.getUname());

        String companyName = UserThreadLocal.getCname();
        try {
            if (!StringUtils.isEmpty(companyName)){
                companyName = URLEncoder.encode(companyName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException error!", e);
        }
        template.header("cname", companyName);


    }
}
