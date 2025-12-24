package ru.kata.spring.boot_security.demo.dao;







import ru.kata.spring.boot_security.demo.entity.User;


import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

    User findByName(String name);

    Optional<User> findByEmail(String email);

}
