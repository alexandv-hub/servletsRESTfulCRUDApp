package com.servletsRESTfulCRUDApp.service.impl;

import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.UserRepository;
import com.servletsRESTfulCRUDApp.service.UserService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> update(User user) {
        return userRepository.update(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userRepository.deleteById(id);
    }
}

