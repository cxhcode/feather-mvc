package org.feather4j.framework.transaction;

import org.feather4j.framework.annotation.Aspect;
import org.feather4j.framework.annotation.Transactional;
import org.feather4j.framework.proxy.Proxy;
import org.feather4j.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Created by Jackie on 2015/12/10.
 * Email : chenxinhua@ishehui.com
 */

@Aspect(value = Transactional.class)
public class TransactionProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>();

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object[] methodParams = proxyChain.getMethodParams();
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Connection connection = getConnection();
        try {
            Object o = proxyChain.doProxyChain();
            return o;
        } catch (Exception e) {
            rollback();
            logger.error("doProxy failed", e);
            throw e;
        } finally {
            close();
        }
    }

    private Connection getConnection() {
        return null;
    }

    private void commit() {

    }

    private void rollback() {

    }

    private void close() {

    }
}
