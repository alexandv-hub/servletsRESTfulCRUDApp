package com.servletsRESTfulCRUDApp.service;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.model.User;

public interface EventService extends GenericEntityService<Event> {

    Event prepareNewEntity(User user, String fileName);
}
