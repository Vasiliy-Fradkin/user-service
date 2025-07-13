package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        User user = new User();
        userService.createUser(user);
        verify(userDao, times(1)).createUser(user);
    }

    @Test
    void getUserById() {
        User user = new User();
        when(userDao.getUserById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        verify(userDao, times(1)).getUserById(1);
    }

    @Test
    void getAllUsers() {
        when(userDao.getAllUsers()).thenReturn(List.of(new User(), new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    void updateUser() {
        User user = new User();
        userService.updateUser(user);
        verify(userDao, times(1)).updateUser(user);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1);
        verify(userDao, times(1)).deleteUser(1);
    }
}

