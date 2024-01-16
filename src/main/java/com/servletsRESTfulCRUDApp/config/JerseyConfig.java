package com.servletsRESTfulCRUDApp.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

@WebListener
public class JerseyConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ServletRegistration.Dynamic registration = ctx.addServlet("Jersey Web Application", ServletContainer.class);
        registration.addMapping("/api/*");
        registration.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, AppResourceConfig.class.getName());
    }
}
