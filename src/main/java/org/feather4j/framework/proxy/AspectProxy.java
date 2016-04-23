package org.feather4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Jackie on 2015/12/8.
 * Email : chenxinhua@ishehui.com
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object[] methodParams = proxyChain.getMethodParams();
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                Object o = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams, o);
            }
        } catch (Exception e) {
            logger.error("doProxy failed", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        } finally {
            end();
        }
        return null;
    }

    public boolean intercept(Class<?> aClass, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void begin() {

    }

    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {

    }

    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams, Object result) {

    }

    public void end() {

    }

    public void error(Class<?> targetClass, Method targetMethod, Object[] methodParams, Throwable e) {

    }
}
