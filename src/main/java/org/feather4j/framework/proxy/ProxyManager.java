package org.feather4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

/**
 * Created by Jackie on 2015/12/8.
 * Email : chenxinhua@ishehui.com
 */
public class ProxyManager {

    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, (MethodInterceptor) (obj, method, args, proxy) ->
                new ProxyChain(targetClass, obj, method, proxy, args, proxyList).doProxyChain());
    }
}
