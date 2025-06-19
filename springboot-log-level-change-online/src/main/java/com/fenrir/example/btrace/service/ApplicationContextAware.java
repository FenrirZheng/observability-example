package com.fenrir.example.btrace.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAware implements org.springframework.context.ApplicationContextAware {


    private static ApplicationContext springContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextAware.springContext = applicationContext;
    }

    public static ApplicationContext getSpringContext() {
        return springContext;
    }
}
