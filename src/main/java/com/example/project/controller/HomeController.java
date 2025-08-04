package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
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
    public String showTutorProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());

        // Optional: double-check in controller level if needed
        if (user.getRole() != 2) { // not a tutor
            return "redirect:/access-denied"; // make this page if needed
        }

        model.addAttribute("user", user);
        return "tutor-profile";
    }

    @PostMapping("/tutor-profile")
    public String updateTutorProfile(@AuthenticationPrincipal UserDetails userDetails,
                                     @ModelAttribute("user") User updatedUser) {
        userService.updateUserProfile(userDetails.getUsername(), updatedUser);
        return "redirect:/tutor-profile";
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