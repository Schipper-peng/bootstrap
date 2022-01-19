package ru.kata.spring.boot_security.demo.dao;




import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);

    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);

    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User findByName(String username) {
        return entityManager.createQuery("from User where name=:name", User.class)
                .setParameter("name", username)
                .getSingleResult();
    }
}
