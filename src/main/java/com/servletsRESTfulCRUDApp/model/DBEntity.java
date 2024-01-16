package com.servletsRESTfulCRUDApp.model;

public interface DBEntity {

    Long getId();
    void setId(Long id);

    Status getStatus();
    void setStatus(Status status);
}
