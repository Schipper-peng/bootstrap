package ru.kata.spring.boot_security.demo.service;




import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService{

    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

    User findByName(String name);

    void updateUser(int id, String name, String surname, Integer age, String email, String password, List<Long> roleIds);

    Optional<User> findByEmail(String email);
}
