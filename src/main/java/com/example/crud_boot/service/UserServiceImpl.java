package com.example.crud_boot.service;


import com.example.crud_boot.dao.RoleDao;
import com.example.crud_boot.dao.UserDao;
import com.example.crud_boot.model.User;
import com.example.crud_boot.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.scope.Scope;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private UserDao userDao;
    private RoleDao roleDao;

    public EntityManager getEm() {
        return em;
    }
    @Autowired
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @PersistenceContext
    private EntityManager em;


    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public UserServiceImpl() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        userDao.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getUser(long id) {
        Optional<User> userOpt = userDao.findById(id);
        User user = userOpt.get();
        return user;
    }

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public void deleteUser(String id) throws SQLException {
        userDao.deleteById(Long.parseLong(id));
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userDao.save(user);
    }

    @Override
    public Iterable<UserRole> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public UserRole getRoleByName(String name) {
        Iterable<UserRole> roles = roleDao.findAll();
        for (UserRole role : roles) {
            if(role.getRolename().equals(name))return role;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      /*  Iterable<User> users = userDao.findAll();
        for (User user : users  ) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }*/
        User user = (User)(em.createQuery(
                "from User where username='"
                        + username + "'").getSingleResult());
        em.close();
        return user;
    }
}
