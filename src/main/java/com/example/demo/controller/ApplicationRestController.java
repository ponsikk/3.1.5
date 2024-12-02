package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.errors.ErrorResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "",produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
                .map(user -> new UserDto(user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(), user.getRoles(), user.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> insert(@Valid @RequestBody User user, BindingResult bindingResult) {
        return ResponseEntity.ok(userService.addUser(user, bindingResult));
    }


    @RequestMapping(value = "/{id}",  produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setRoles(userDto.getRoles());  // добавление ролей
            userService.updateUser(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();  // Если пользователь не найден
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDto userDto = new UserDto(user.getFirstName(),
                        user.getLastName(), user.getEmail(), user.getRoles(),
                        user.getId());
                return ResponseEntity.ok(userDto);
            }
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid user ID format");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


}

