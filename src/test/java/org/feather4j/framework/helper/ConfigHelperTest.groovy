package org.feather4j.framework.helper

/**
 *
 * Created by Jackie on 2015/11/25.
 * Email : chenxinhua@ishehui.com
 *
 */
class ConfigHelperTest extends spock.lang.Specification {
    def "GetJdbcDriver"() {
        setup:
        HelperLoader.initHelper()
    }

    def "test map"() {
        setup:
        def map = [:]
        map.a = "a"
        expect:
        map.a > "1"
    }

    def "GetJdbcUrl"() {

    }

    def "GetJdbcUsername"() {

    }

    def "GetJdbcPassword"() {

    }

    def "GetAppBasePackage"() {

    }

    def "GetAppJspPath"() {

    }

    def "AppAssetPath"() {

    }

    def "Main"() {

    }
}
