package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}

