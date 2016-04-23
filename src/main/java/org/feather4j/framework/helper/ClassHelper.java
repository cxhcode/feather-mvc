package org.feather4j.framework.helper;

import org.feather4j.framework.annotation.Controller;
import org.feather4j.framework.annotation.Repository;
import org.feather4j.framework.annotation.Service;
import org.feather4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class ClassHelper {

    private ClassHelper() {
    }

    private static Set<Class<?>> CLASS_SET;

    static {
        CLASS_SET = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage());
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classes = new HashSet<>();
        CLASS_SET.forEach(it -> {
                    if (it.isAnnotationPresent(Controller.class)) {
                        classes.add(it);
                    }
                }
        );
        return classes;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classes = new HashSet<>();
        CLASS_SET.forEach(it -> {
                    if (it.isAnnotationPresent(Service.class)) {
                        classes.add(it);
                    }
                }
        );
        return classes;
    }

    public static Set<Class<?>> getRepositoryClassSet() {
        Set<Class<?>> classes = new HashSet<>();
        CLASS_SET.forEach(it -> {
                    if (it.isAnnotationPresent(Repository.class)) {
                        classes.add(it);
                    }
                }
        );
        return classes;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classes = new HashSet<>();
        classes.addAll(getControllerClassSet());
        classes.addAll(getServiceClassSet());
        classes.addAll(getRepositoryClassSet());
        return classes;
    }

    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        HashSet<Class<?>> classes = new HashSet<>();
        CLASS_SET.forEach(it -> {
            if (superClass.isAssignableFrom(it) && !superClass.equals(it)) {
                classes.add(it);
            }
        });
        return classes;
    }

    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        HashSet<Class<?>> classes = new HashSet<>();
        CLASS_SET.forEach(it -> {
            if (it.isAnnotationPresent(annotationClass)) {
                classes.add(it);
            }
        });
        return classes;
    }
}
