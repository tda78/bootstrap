package com.example.crud_boot.dao;

import com.example.crud_boot.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<UserRole, Long> {
}
