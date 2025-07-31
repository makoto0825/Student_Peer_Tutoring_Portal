package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/tutor")
    public String tutor() {
        return "tutor";
    }

    @GetMapping("/tutor-profile")
    public String tutorProfile() {
        return "tutor-profile";
    }

    @GetMapping("/student")
    public String student() {
        return "student";
    }

    @GetMapping("/session-booking")
    public String sessionBooking(Model model) {
        // Add any necessary attributes to the model
        return "session-booking";
    }
}