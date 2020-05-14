package com.example.crud_boot.service;

import com.example.crud_boot.model.User;
import com.example.crud_boot.model.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    public void deleteUser(String id) throws SQLException;

    public void updateUser(User user) throws SQLException;

    public Iterable<UserRole> getAllRoles();

    public UserRole getRoleByName(String name);

}
