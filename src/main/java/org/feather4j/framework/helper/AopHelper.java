package org.feather4j.framework.helper;

import org.feather4j.framework.annotation.Aspect;
import org.feather4j.framework.proxy.AspectProxy;
import org.feather4j.framework.proxy.Proxy;
import org.feather4j.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by Jackie on 2015/12/9.
 * Email : chenxinhua@ishehui.com
 */
public class AopHelper {

    private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            createTargetMap(proxyMap).forEach((targetClass, proxyList) -> {
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            });
        } catch (Exception e) {
            logger.error("init aop failed", e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        HashSet<Class<?>> classes = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            classes.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classes;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(AspectProxy.class);
        classSetBySuper.forEach(it -> {
            if (it.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = it.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(it, targetClassSet);
            }
        });
        return proxyMap;
    }


    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> classListMap = new HashMap<>();

        proxyMap.entrySet().forEach(classSetEntry -> {
            Class<?> proxyClass = classSetEntry.getKey();
            Set<Class<?>> targetClass = classSetEntry.getValue();
            targetClass.forEach(aClass -> {
                Proxy proxy = null;
                try {
                    proxy = (Proxy) proxyClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("create Target  Map failed...", e);
                    throw new RuntimeException(e);
                }
                if (!classListMap.containsKey(proxyClass)) {
                    ArrayList<Proxy> proxies = new ArrayList<>();
                    classListMap.put(aClass, proxies);
                }
                classListMap.get(aClass).add(proxy);
            });
        });
        return classListMap;
    }

}
