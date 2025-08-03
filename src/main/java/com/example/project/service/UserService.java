package com.example.project.service;

import com.example.project.entity.Department;
import com.example.project.entity.User;
import com.example.project.repository.DepartmentRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email, String firstName, String lastName, String description, Long departmentId, int role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("This username is already in use");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("This email address is already in use");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Invalid department ID"));

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email, firstName, lastName, description, department);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateUserProfile(String username, User updatedUser) {
        User user = findByUsername(username);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setDescription(updatedUser.getDescription());

        if (updatedUser.getDepartment() != null) {
            user.setDepartment(updatedUser.getDepartment());
        }

        userRepository.save(user);
    }
}
