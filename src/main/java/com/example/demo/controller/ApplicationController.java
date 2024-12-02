package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/admin-page")
    public String showAdminPage() {
        return "admin-page";  // Вернёт admin-page.html из папки resources/static
    }
}
