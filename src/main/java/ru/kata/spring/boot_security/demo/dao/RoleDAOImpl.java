package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(List<Long> id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findByIds(List<Long> ids) {
        return entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
