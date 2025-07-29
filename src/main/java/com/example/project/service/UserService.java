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
        // ユーザー名とメールアドレスの重複チェック
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("このユーザー名は既に使用されています");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("このメールアドレスは既に使用されています");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email, firstName, lastName);
        user.setRole(role);
        return userRepository.save(user);
    }
} 