package com.example.project.controller;

import com.example.project.entity.Department;
import com.example.project.entity.Session;
import com.example.project.entity.User;
import com.example.project.service.SessionService;
import com.example.project.service.UserService;
import com.example.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserService userService;
    private final SessionService sessionService;
    private final DepartmentService departmentService;

    @Autowired
    public HomeController(UserService userService, SessionService sessionService, DepartmentService departmentService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}