package com.example.crud_boot.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    private String rolename;

    public UserRole() {
    }

    public UserRole(String rolename) {
        this.rolename = rolename;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String role_name) {
        this.rolename = role_name;
    }

    public String toString(){
        return getRolename();
    }

    @Override
    public String getAuthority() {
        return rolename;
    }

}
