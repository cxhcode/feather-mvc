package org.feather4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Jackie on 2015/12/2.
 * Email : chenxinhua@ishehui.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
