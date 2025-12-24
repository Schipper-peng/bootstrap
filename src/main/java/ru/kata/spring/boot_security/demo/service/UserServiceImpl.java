package ru.kata.spring.boot_security.demo.service;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);

    }
    @Override
    public User getUser(int id) {
        return userDAO.getUser(id);
    }
    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public User findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public void updateUser(int id, String name, String surname, Integer age, String email, String password, List<Long> roleIds) {
        User user = userDAO.getUser(id);
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setEmail(email);
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        Set<Role> roles = new HashSet<>(roleDAO.findByIds(roleIds));
        user.setRoles(roles);
        userDAO.saveUser(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with: " + email));
    }
}
