package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    List<String> getRolesNames(User user);
    void addNewRole(Role role);
    Role findByName(String roleName);
}
