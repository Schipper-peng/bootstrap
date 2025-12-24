package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(List<Long> id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    public List<Role> findByIds(List<Long> ids) {
        return roleDAO.findByIds(ids);
    }


}
