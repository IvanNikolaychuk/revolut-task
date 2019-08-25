package com.revolut.money.transfer.infrastructure.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionProvider {
    private static SessionProvider entity;
    private SessionFactory sessionFactory;

    public static SessionProvider get() {
        if (entity == null) entity = new SessionProvider();
        return entity;
    }

    private SessionProvider() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

}
