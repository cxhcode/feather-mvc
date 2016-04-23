package org.feather4j.framework.helper;

import org.feather4j.framework.util.ReflectionUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class BeanHelper {

    private static ConcurrentHashMap<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    static {
        ClassHelper.getBeanClassSet().forEach(it -> {
            Object o = ReflectionUtil.newInstance(it);
            BEAN_MAP.put(it, o);
        });
    }

    public static ConcurrentHashMap<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> tClass) {
        if (!BEAN_MAP.containsKey(tClass)) {
            throw new RuntimeException("can't get bean by class: " + tClass);
        }
        return (T) BEAN_MAP.get(tClass);
    }

    public static void setBean(Class<?> tClass, Object obj) {
        BEAN_MAP.put(tClass, obj);
    }

    private BeanHelper() {
    }
}
