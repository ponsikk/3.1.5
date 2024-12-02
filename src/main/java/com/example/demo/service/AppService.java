package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
public class UserService implements UserDetailsService {



    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        // Проверяем и добавляем пользователя с ролью USER
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setFirstName("Ivan");
            user.setLastName("Userov");
            user.setEmail("user@email.com");

            String encodedPassword = passwordEncoder.encode("userpass");
            user.setPassword(encodedPassword);
            user.setRoles(Set.of(Role.ROLE_USER));

            userRepository.save(user);
        }

        // Проверяем и добавляем администратора с ролью ADMIN
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFirstName("Anna");
            admin.setLastName("Adminova");
            admin.setEmail("admin@email.com");

            String encodedPassword = passwordEncoder.encode("adminpass");
            admin.setPassword(encodedPassword);
            admin.setRoles(Set.of(Role.ROLE_ADMIN));

            userRepository.save(admin);
        }

        // Проверяем и добавляем пользователя с обеими ролями (USER и ADMIN)
        if (userRepository.findByUsername("superuser").isEmpty()) {
            User superuser = new User();
            superuser.setUsername("superuser");
            superuser.setFirstName("Super");
            superuser.setLastName("User");
            superuser.setEmail("superuser@email.com");

            String encodedPassword = passwordEncoder.encode("superpass");
            superuser.setPassword(encodedPassword);
            superuser.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));

            userRepository.save(superuser);
        }

        // Проверяем и добавляем ещё одного пользователя с ролью USER
        if (userRepository.findByUsername("guest").isEmpty()) {
            User guest = new User();
            guest.setUsername("guest");
            guest.setFirstName("Guest");
            guest.setLastName("Guestov");
            guest.setEmail("guest@email.com");

            String encodedPassword = passwordEncoder.encode("guestpass");
            guest.setPassword(encodedPassword);
            guest.setRoles(Set.of(Role.ROLE_GUEST));

            userRepository.save(guest);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(User user, BindingResult bindingResult) {
        // Проводим валидацию вручную, если есть ошибки
        if (bindingResult.hasErrors()) {
            // Обработка ошибок, если необходимо
            return null;  // Или выбросить исключение, если валидация не прошла
        }
        return userRepository.save(user);  // Сохраняем пользователя в базе
    }


    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}