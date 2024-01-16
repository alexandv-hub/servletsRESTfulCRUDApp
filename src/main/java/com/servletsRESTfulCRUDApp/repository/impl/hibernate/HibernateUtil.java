package com.servletsRESTfulCRUDApp.repository.impl.hibernate;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_WHEN_SETTING_UP_HIBERNATE_SESSION_FACTORY;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_HIBERNATE_SET_UP_FINISHED_SUCCESSFULLY;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_STARTING_HIBERNATE_SET_UP;

@Getter
@Slf4j
public class HibernateUtil {

    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            setUp();
        }
        return sessionFactory;
    }

    private static void setUp() {
        log.info(INFO_STARTING_HIBERNATE_SET_UP);
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            sessionFactory =
                    new MetadataSources(registry)
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(Event.class)
                            .addAnnotatedClass(File.class)
                            .buildMetadata()
                            .buildSessionFactory();

            log.info(INFO_HIBERNATE_SET_UP_FINISHED_SUCCESSFULLY);
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            log.error(ERR_WHEN_SETTING_UP_HIBERNATE_SESSION_FACTORY);
            e.printStackTrace(System.out);
            throw e;
        }
    }
}
