package com.servletsRESTfulCRUDApp.repository.impl.hibernate;

import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibernateUtil.getSessionFactory;
import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.SQLQueries.HQL_FROM_USER_BY_ID_LEFT_JOIN_FETCH_EVENTS;
import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.SQLQueries.HQL_FROM_USER_LEFT_JOIN_FETCH_EVENTS;

public class HibUserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> save(User user) {
        getSessionFactory().inTransaction(session ->
                session.persist(user));
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        return getSessionFactory().fromTransaction(session ->
                session.createQuery(HQL_FROM_USER_LEFT_JOIN_FETCH_EVENTS, User.class)
                        .getResultList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return getSessionFactory().fromTransaction(session -> {
            boolean isUserExist = session.get(User.class, id) != null;
            if (isUserExist) {
                User foundUser = session.createSelectionQuery(HQL_FROM_USER_BY_ID_LEFT_JOIN_FETCH_EVENTS, User.class)
                        .setParameter("id", id)
                        .getSingleResult();
                return Optional.of(foundUser);
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.ofNullable(getSessionFactory().fromTransaction(session ->
                session.merge(user)));
    }

    @Override
    public boolean deleteById(Long id) {
        return getSessionFactory().fromTransaction(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                return true;
            }
            return false;
        });
    }
}
