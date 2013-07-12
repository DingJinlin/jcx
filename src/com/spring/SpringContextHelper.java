package com.spring;

import com.vaadin.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * User: Administrator
 * Date: 13-3-14
 * Time: 上午11:27
 */
public class SpringContextHelper {
    private ApplicationContext context;

    public SpringContextHelper(Application application) {
//        ServletContext servletContext = ((WebApplicationContext)application.getContext()).getHttpSession()
//                .getServletContext();
//        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        String[] configLocations = new String[] {
                "classpath:com/djl/jcx/data/sqlmaps/applicationContext-resources.xml",
                "classpath:com/djl/jcx/data/sqlmaps/applicationContext-ibatis.xml"};
        context = new ClassPathXmlApplicationContext(configLocations);
    }

    public <T> T getBean(final String s, Class<T> tClass) {
        return context.getBean(s, tClass);
    }
}
