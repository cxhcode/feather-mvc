package org.feather4j.framework.proxy;

/**
 * Created by Jackie on 2015/12/2.
 * Email : chenxinhua@ishehui.com
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
