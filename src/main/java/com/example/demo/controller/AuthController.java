package com.example.demo.controller;

import com.example.demo.model.User;

import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@EnableAutoConfiguration
public class AuthController {


    @GetMapping
    public String showHomePage() {
        return "/;
    }
}
