package com.mw.gateway.token;


/**
 * 线程变量缓存Local
 * @author xufan1
 */
public class UserThreadLocal extends ThreadLocal<UserToken>{

    private static ThreadLocal<UserToken> threadLocal = new ThreadLocal<UserToken>();

    public static void setLocal(UserToken userToken){
        threadLocal.set(userToken);
    }

    public static UserToken getLocal(){
        return threadLocal.get();
    }

    public static void removeLocal(){
        threadLocal.remove();
    }

    public static String getToken(){
        UserToken userToken=threadLocal.get();
        if (userToken != null){
            return userToken.getToken();
        }
        return null;
    }

    public static Long getUid(){
        UserToken userToken=threadLocal.get();
        if (userToken != null){
            return userToken.getUid();
        }
        return null;
    }

    public static Long getCid(){
        UserToken userToken=threadLocal.get();
        if (userToken != null){
            return userToken.getCid();
        }
        return null;
    }

    public static Long getStaffId(){
        UserToken userToken=threadLocal.get();
        if (userToken != null){
            return userToken.getStaffId();
        }
        return null;
    }

    public static String getUname(){
        UserToken userToken = threadLocal.get();
        if (userToken != null){
            return userToken.getUname();
        }
        return null;
    }

    public static String getCname(){
        UserToken userToken = threadLocal.get();
        if (userToken != null){
            return userToken.getCname();
        }
        return null;
    }

    public static Long getSysId(){
        UserToken userToken = threadLocal.get();
        if (userToken != null){
            return userToken.getSysId();
        }
        return null;
    }


    public static String getMqId(){
        UserToken userToken = threadLocal.get();
        if (userToken != null){
            return userToken.getMqId();
        }
        return null;
    }
}

