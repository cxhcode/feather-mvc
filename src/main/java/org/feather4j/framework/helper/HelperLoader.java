package org.feather4j.framework.helper;

import org.feather4j.framework.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class HelperLoader {


    public static void initHelper() {
        List<Class> classes = new ArrayList<>();
        classes.add(BeanHelper.class);
        classes.add(ClassHelper.class);
        classes.add(ConfigHelper.class);
        classes.add(AopHelper.class);
        classes.add(IocHelper.class);
        classes.add(ControllerHelper.class);
        classes.forEach(aClass -> ClassUtil.loadClass(aClass.getName(), true)
        );
    }
}
