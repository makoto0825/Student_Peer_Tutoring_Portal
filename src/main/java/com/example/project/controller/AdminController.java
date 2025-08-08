package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showAdminPage(Model model) {
        List<User> pendingTutors = userRepository.findByRoleAndVerified(2, false);
        model.addAttribute("pendingTutors", pendingTutors);
        return "admin";
    }

    @PostMapping("/verify/{id}")
    public String verifyTutor(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(tutor -> {
            if (tutor.getRole() == 2) {
                tutor.setVerified(true);
                userRepository.save(tutor);
            }
        });
        return "redirect:/admin";
    }

    @PostMapping("/deny/{id}")
    public String denyTutor(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(tutor -> {
            if (tutor.getRole() == 2) {
                userRepository.deleteById(id);
            }
        });
        return "redirect:/admin";
    }

}
