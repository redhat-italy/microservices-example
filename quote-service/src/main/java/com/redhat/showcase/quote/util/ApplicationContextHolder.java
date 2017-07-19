package com.redhat.showcase.quote.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This utility class is used to let lookup for bean created in ApplicationContext outside spring eg:
 * <p>
 * The QuoteServiceImpl will be created by Cxf and not by Spring so we need a way to access SpringBoot beans from inside this class
 */

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}