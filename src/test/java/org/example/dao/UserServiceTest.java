package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void testCreateUser_Valid() {
        User user = new User("Bob", "bob@example.com", 30);
        userService.createUser(user);
        verify(userDao, times(1)).create(user);
    }

    @Test
    void testCreateUser_InvalidEmail() {
        User user = new User("NoEmail", "", 20);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        verify(userDao, never()).create(user);
    }

    @Test
    void testGetUser() {
        User user = new User("Alice", "alice@example.com", 25);
        user.setId(1L);

        when(userDao.read(1L)).thenReturn(user);

        User found = userService.getUser(1L);
        assertEquals("Alice", found.getName());
    }

    @Test
    void testGetAllUsers() {
        when(userDao.readAll()).thenReturn(List.of(new User("Tom", "tom@example.com", 40)));
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }
}
