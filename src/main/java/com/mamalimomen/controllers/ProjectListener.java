package com.mamalimomen.controllers;

import com.mamalimomen.controllers.utilities.AppManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AppManager.startApp();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        AppManager.endApp();
    }
}
