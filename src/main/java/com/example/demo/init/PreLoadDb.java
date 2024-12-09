package com.example.demo.init;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class PreLoadDb {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public PreLoadDb(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        Role roleUser = roleService.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.addNewRole(roleUser);
        }

        Role roleAdmin = roleService.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.addNewRole(roleAdmin);
        }

        User admin = userService.getUserByUsername("admin");
        if (admin == null) {
            admin = new User("admin", "admin", "Admin",
                    "Adminov", "admin@mail.ru", Set.of(roleAdmin, roleUser));
            userService.addUser(admin);
        }

        User user = userService.getUserByUsername("user");
        if (user == null) {
            user = new User("user", "user", "User",
                    "Userov", "user@mail.ru", Set.of(roleUser));
            userService.addUser(user);
        }

    }
}