package com.mw.gateway.handle.hystrix;

import com.mw.gateway.token.UserThreadLocal;
import com.mw.gateway.token.UserToken;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 自定义 HystrixConcurrencyStrategy 策略，
 * 将UserThreadLocal 传入线程中
 */
@Component
public class HystrixConcurrencyStrategyCustomize extends HystrixConcurrencyStrategy {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        UserToken userToken = UserThreadLocal.getLocal();
        HystrixCustomizeCallable hystrixCustomizeCallable = new HystrixCustomizeCallable(userToken, callable);
        return hystrixCustomizeCallable;
    }

}

