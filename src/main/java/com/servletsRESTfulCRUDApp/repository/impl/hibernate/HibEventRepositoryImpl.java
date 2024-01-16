package com.servletsRESTfulCRUDApp.repository.impl.hibernate;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.repository.EventRepository;

import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibernateUtil.getSessionFactory;
import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.SQLQueries.HQL_FROM_EVENT_BY_ID_LEFT_JOIN_FETCH_FILE_FETCH_USER;
import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.SQLQueries.HQL_FROM_EVENT_LEFT_JOIN_FETCH_FILE_FETCH_USER;

public class HibEventRepositoryImpl implements EventRepository {

    @Override
    public Optional<Event> save(Event event) {
        getSessionFactory().inTransaction(session ->
                session.merge(event));
        return Optional.of(event);
    }

    @Override
    public List<Event> findAll() {
        return getSessionFactory().fromTransaction(session ->
                session.createSelectionQuery(HQL_FROM_EVENT_LEFT_JOIN_FETCH_FILE_FETCH_USER, Event.class)
                .getResultList());
    }

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(getSessionFactory().fromTransaction(session ->
                session.createSelectionQuery(HQL_FROM_EVENT_BY_ID_LEFT_JOIN_FETCH_FILE_FETCH_USER, Event.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public Optional<Event> update(Event event) {
        return Optional.ofNullable(getSessionFactory().fromTransaction(session ->
                session.merge(event)));
    }

    @Override
    public boolean deleteById(Long id) {
        return getSessionFactory().fromTransaction(session -> {
            Event event = session.get(Event.class, id);
            if (event != null) {
                session.remove(event);
                return true;
            }
            return false;
        });
    }
}
