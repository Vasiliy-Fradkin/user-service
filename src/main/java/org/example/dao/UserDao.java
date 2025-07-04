package org.example.dao;

import org.example.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void createUser(User user);
    Optional<User> getUserById(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
}
