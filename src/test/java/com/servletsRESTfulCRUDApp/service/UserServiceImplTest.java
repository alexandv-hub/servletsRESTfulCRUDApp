package com.servletsRESTfulCRUDApp.service;

import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.UserRepository;
import com.servletsRESTfulCRUDApp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {
        User user = User.builder().build();
        when(userRepository.save(any(User.class))).thenReturn(Optional.ofNullable(user));

        Optional<User> savedUser = userService.save(user);

        assertNotNull(savedUser);
        verify(userRepository).save(user);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        User user = User.builder().build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(id);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository).findById(id);
    }

    @Test
    void testFindAll() {
        User user = User.builder().build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.findAll();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser() {
        User user = User.builder().build();
        when(userRepository.update(user)).thenReturn(Optional.of(user));

        Optional<User> updatedUser = userService.update(user);

        assertTrue(updatedUser.isPresent());
        verify(userRepository).update(user);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        when(userRepository.deleteById(id)).thenReturn(true);

        boolean isDeleted = userService.deleteById(id);

        assertTrue(isDeleted);
        verify(userRepository).deleteById(id);
    }
}
