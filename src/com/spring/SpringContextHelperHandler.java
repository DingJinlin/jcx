package com.spring;

import com.vaadin.Application;

/**
 * User: Administrator
 * Date: 13-3-25
 * Time: 下午3:00
 */
public class SpringContextHelperHandler {
    static SpringContextHelper sch;

    public SpringContextHelperHandler(Application application) {
        sch = new SpringContextHelper(application);
    }

    public static SpringContextHelper getSch() {
        return sch;
    }
}
