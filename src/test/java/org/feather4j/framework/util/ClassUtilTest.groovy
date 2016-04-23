package org.feather4j.framework.util

import spock.lang.Specification

/**
 *
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 *
 */
class ClassUtilTest extends Specification {
    def "LoadClass"() {
        def set
        setup:
        set = ClassUtil.getClassSet("org.feather4j.framework");
        set.forEach(){
            println it
        }
    }
}
