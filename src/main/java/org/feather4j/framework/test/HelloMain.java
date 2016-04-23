package org.feather4j.framework.test;

import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2015/11/27.
 * Email : chenxinhua@ishehui.com
 */
public class HelloMain {

    public static void main(String[] args) {
        HelloImpl hello = new HelloImpl();
        HelloDynamicProxy helloDynamicProxy = new HelloDynamicProxy(hello);
        Hello helloDynamicProxyProxy = helloDynamicProxy.getProxy();
        helloDynamicProxyProxy.say();
    }
}
