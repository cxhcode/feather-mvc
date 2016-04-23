package org.feather4j.framework.proxy

import org.feather4j.framework.helper.ConfigHelper


/**
 *
 * Created by Jackie on 2015/12/8.
 * Email : chenxinhua@ishehui.com
 *
 */
class ControllerProxyTest extends spock.lang.Specification {
    def "Before"() {

    }

    def "After"() {
        setup:
        def format = String.format("time : %dms", System.currentTimeMillis())
        expect:
        format == "hello"
    }
}
