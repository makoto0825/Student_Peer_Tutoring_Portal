package com.example.project.controller;

import com.example.project.entity.Department;
import com.example.project.entity.User;
import com.example.project.service.DepartmentService;
import com.example.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public UserController(UserService userService, DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        List<Department> departments = departmentService.findAll(); // maybe sort by name
        model.addAttribute("departments", departments);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String description,
                               @RequestParam Long departmentId,
                               @RequestParam int role,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(username, password, email, firstName, lastName, description, departmentId, role);
            if (role == 2) {
                redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please wait for admin verification.");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in.");
            }
            return  "redirect:/register-success";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "register";
    }

    @GetMapping("/register-success")
    public String registerSuccess() {
        return "register_success";
    }
}
