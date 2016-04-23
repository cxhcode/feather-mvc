package org.feather4j.framework.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2015/11/27.
 * Email : chenxinhua@ishehui.com
 */
public class HelloDynamicProxy implements InvocationHandler {

    private Object target;

    public HelloDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(target, args);
        after();
        return invoke;
    }

    public <T> T getProxy() {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
        return (T) o;
    }


    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

}
