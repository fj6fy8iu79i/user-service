package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User read(Long id);
    List<User> readAll();
    void update(User user);
    void delete(Long id);
}
