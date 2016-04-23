package org.feather4j.framework.test;

import org.feather4j.framework.annotation.Controller;
import org.feather4j.framework.annotation.Inject;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
@Controller
public class MyController {

    @Inject
    private MyService myService;
}
