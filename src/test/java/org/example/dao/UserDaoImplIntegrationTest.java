package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDaoImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test")
                    .withInitScript("schema.sql");;

    private static UserDaoImpl userDao;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        userDao = new UserDaoImpl();
    }

    @AfterAll
    static void tearDownAll() {
        HibernateUtil.shutdown();
    }

    @Test
    void createAndGetUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        userDao.createUser(user);

        Optional<User> retrieved = userDao.getUserById(user.getId());

        assertTrue(retrieved.isPresent());
        assertEquals("Test User", retrieved.get().getName());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setName("Update Test");
        user.setEmail("update@example.com");
        user.setAge(25);
        user.setCreatedAt(LocalDateTime.now());

        userDao.createUser(user);

        user.setName("Updated Name");
        userDao.updateUser(user);

        Optional<User> retrieved = userDao.getUserById(user.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Updated Name", retrieved.get().getName());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setName("Delete Test");
        user.setEmail("delete@example.com");
        user.setAge(22);
        user.setCreatedAt(LocalDateTime.now());

        userDao.createUser(user);

        userDao.deleteUser(user.getId());

        Optional<User> retrieved = userDao.getUserById(user.getId());
        assertTrue(retrieved.isEmpty());
    }
}
