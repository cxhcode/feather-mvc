package org.feather4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.feather4j.framework.annotation.Action;
import org.feather4j.framework.bean.Handler;
import org.feather4j.framework.bean.Request;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class ControllerHelper {
    private static final ConcurrentHashMap<Request, Handler> ACTION_MAP = new ConcurrentHashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            controllerClassSet.forEach(aClass -> {
                Method[] declaredMethods = aClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(declaredMethods)) {
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(Action.class)) {
                            Action annotation = declaredMethod.getAnnotation(Action.class);
                            String mapping = annotation.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] split = mapping.split(":");
                                Request request = new Request(StringUtils.trim(split[0].toLowerCase()), StringUtils.trim(split[1]));
                                Handler handler = new Handler(aClass, declaredMethod);
                                ACTION_MAP.put(request, handler);
                            }
                        }
                    }
                }
            });
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
