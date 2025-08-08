package com.example.project.service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email, String firstName, String lastName, int role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("This username is already in use");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("This email address is already in use");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, email, firstName, lastName, role);
        user.setVerified(role == 1); // students auto-verified, tutors not
        return userRepository.save(user);
    }

} 