package org.feather4j.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.feather4j.framework.annotation.Inject;
import org.feather4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class IocHelper {

    static {
        ConcurrentHashMap<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            beanMap.entrySet().forEach(classObjectEntry -> {
                Class<?> aClass = classObjectEntry.getKey();
                Object instance = classObjectEntry.getValue();
                Field[] fields = aClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> type = field.getType();
                            Object value = beanMap.get(type);
                            if (value != null) {
                                ReflectionUtil.setField(instance, field, value);
                            }
                        }
                    }
                }
            });
        }
    }
}
