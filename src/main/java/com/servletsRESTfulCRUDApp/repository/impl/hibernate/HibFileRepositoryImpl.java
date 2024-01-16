package com.servletsRESTfulCRUDApp.repository.impl.hibernate;

import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.repository.FileRepository;

import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.HibernateUtil.getSessionFactory;

public class HibFileRepositoryImpl implements FileRepository {

    @Override
    public Optional<File> save(File file) {
        getSessionFactory().inTransaction(session ->
                session.persist(file));
        return Optional.of(file);
    }

    @Override
    public List<File> findAll() {
        return getSessionFactory().fromTransaction(session ->
                session.createSelectionQuery("from File", File.class)
                        .getResultList());
    }

    @Override
    public Optional<File> findById(Long id) {
        return Optional.ofNullable(getSessionFactory().fromTransaction(session ->
                session.get(File.class, id)));
    }

    @Override
    public Optional<File> update(File file) {
        return getSessionFactory().fromTransaction(session -> {
            File existingFile = session.find(File.class, file.getId());
            if (existingFile != null) {
                session.merge(file);
                return Optional.of(file);
            }
            return Optional.empty();
        });
    }

    @Override
    public boolean deleteById(Long id) {
        return getSessionFactory().fromTransaction(session -> {
            File file = session.get(File.class, id);
            if (file != null) {
                session.remove(file);
                return true;
            }
            return false;
        });
    }
}
