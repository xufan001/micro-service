package com.mw.gateway.handle.hystrix;

import com.mw.gateway.token.UserThreadLocal;
import com.mw.gateway.token.UserToken;

import java.util.concurrent.Callable;

public class HystrixCustomizeCallable<T> implements Callable<T> {

    private UserToken userToken;

    private Callable<T> callable;

    public HystrixCustomizeCallable(UserToken userToken, Callable<T> callable) {
        this.userToken = userToken;
        this.callable = callable;
    }

    @Override
    public T call() throws Exception {
        try {
            if (null != this.userToken) {
                UserThreadLocal.setLocal(userToken);
            }
            return this.callable.call();
        } finally {
            UserThreadLocal.removeLocal();
        }
    }
}