package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u left join fetch u.roles where u.username =:username")
    User findByUsername(String username);

    Optional<User> findById(Long id);
}

