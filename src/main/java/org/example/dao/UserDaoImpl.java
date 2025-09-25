package org.example.dao;

import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void create(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public User read(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            System.err.println("Error reading user: " + e.getMessage());
            return  null;
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.err.println("Error reading all users: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

}
