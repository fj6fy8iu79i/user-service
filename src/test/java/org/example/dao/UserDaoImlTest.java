package org.example.dao;

import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImplTest {
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDaoImpl userDao;

    @BeforeAll
    void setUpAll() {
        postgres.start();

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .setProperty("hibernate.show_sql", "false")
                .addAnnotatedClass(User.class);

        HibernateUtil.overrideSessionFactory(configuration.buildSessionFactory());
        userDao = new UserDaoImpl();
    }

    @AfterAll
    void tearDownAll() {
        postgres.stop();
    }

    @Test
    @Order(1)
    void testCreateAndRead() {
        User user = new User("Alice", "alice@example.com", 25);
        userDao.create(user);

        User saved = userDao.read(user.getId());
        assertNotNull(saved);
        assertEquals("Alice", saved.getName());
    }

    @Test
    @Order(2)
    void testReadAll() {
        List<User> users = userDao.readAll();
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(3)
    void testUpdate() {
        User user = userDao.readAll().get(0);
        user.setName("Updated");
        userDao.update(user);

        User updated = userDao.read(user.getId());
        assertEquals("Updated", updated.getName());
    }

    @Test
    @Order(4)
    void testDelete() {
        User user = userDao.readAll().get(0);
        userDao.delete(user.getId());

        User deleted = userDao.read(user.getId());
        assertNull(deleted);
    }
}
