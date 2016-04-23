package org.feather4j.framework.test;

/**
 * Created by Jackie on 2015/11/27.
 * Email : chenxinhua@ishehui.com
 */
public class HelloProxy implements Hello {
    private Hello hello;

    public HelloProxy() {
        hello = new HelloImpl();
    }

    @Override
    public void say() {
        before();
        hello.say();
        after();
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
