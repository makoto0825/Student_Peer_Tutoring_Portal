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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserService userService;
    private final SessionService sessionService;
    private final DepartmentService departmentService;

    @Autowired
    public StudentController(UserService userService, SessionService sessionService, DepartmentService departmentService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.departmentService = departmentService;
    }

    /**
     * Student dashboard page
     * Shows upcoming and past sessions
     */

    @GetMapping
    public String student(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userService.findByUsername(userDetails.getUsername());

        // Get All sessions for this student
        List<Session> allSessions = sessionService.getStudentSessions(student.getId());

        // Separate into upcoming and past
        List<Session> upcomingSessions = allSessions.stream()
                .filter(session -> session.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        List<Session> pastSessions = allSessions.stream()
                .filter(session -> session.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        
        // Map tutor names for display
        Map<Long, String> tutorNames = new HashMap<>();
        Map<Long, String> tutorDeptNames = new HashMap<>();

        for (Session s : allSessions) {
            Long tId = s.getTutorId();
            if (!tutorNames.containsKey(tId)) {
                User tutor = userService.findById(tId);
                tutorNames.put(tId, tutor.getFirstName() + " " + tutor.getLastName());

                Department dept = departmentService.findById(tutor.getDepartmentId());
                tutorDeptNames.put(tId, dept != null ? dept.getName() : "-");
            }
        }

        // Pass to model
        model.addAttribute("upcomingSessions", upcomingSessions);
        model.addAttribute("pastSessions", pastSessions);
        model.addAttribute("tutorNames", tutorNames);
        model.addAttribute("tutorDeptNames", tutorDeptNames);

        return "student";
    }

    @GetMapping("/profile")
    public String showStudentProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());

        // Optional: double-check in controller level if needed
        if (user.getRole() != 1) { // not a student
            return "redirect:/access-denied"; // make this page if needed
        }

        model.addAttribute("user", user);
        return "student_profile";
    }

    @PostMapping("/profile")
    public String updateStudentProfile(@AuthenticationPrincipal UserDetails userDetails,
                                       @ModelAttribute("user") User updatedUser, RedirectAttributes redirectAttributes) {
        userService.updateUserProfile(userDetails.getUsername(), updatedUser);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/student/profile";
    }

    /**
     * Session booking page
     * Allows students to book sessions with tutors
     */
    @GetMapping("/book-session")
    public String bookSessionPage(Model model) {
        // Get all departments
        List<Department> departments = departmentService.findAll();

        // Get all tutors (role = 2)
        List<User> tutors = userService.findTutors();

        // Get all unbooked sessions
        List<Session> availableSessions = sessionService.getAvailableSessions();

        List<Map<String, Object>> sessionData = availableSessions.stream()
            .map(session -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", session.getId());
                map.put("date", session.getDate());
                map.put("timeSlot", session.getTimeSlot());
                map.put("tutorId", session.getTutorId());

                // Get tutor's departmentId
                User tutor = tutors.stream()
                        .filter(t -> t.getId().equals(session.getTutorId()))
                        .findFirst()
                        .orElse(null);
                map.put("departmentId", tutor != null ? tutor.getDepartmentId() : null);

                return map;
            })
            .toList();

        model.addAttribute("departments", departments);
        model.addAttribute("tutors", tutors);
        model.addAttribute("sessions", sessionData);

        return "session-booking";
    }

    @PostMapping("/book-session")
    public String bookSession(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("sessionId") Long sessionId,
                            RedirectAttributes redirectAttributes) {
        try {
            User student = userService.findByUsername(userDetails.getUsername());

            if (student.getRole() != 1) { // Ensure student role
                redirectAttributes.addFlashAttribute("error", "Access denied");
            }
            sessionService.bookSession(sessionId, student.getId());
            redirectAttributes.addFlashAttribute("success", "Session booked successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/book-session";
    }

    @PostMapping("/cancel-session")
    public String cancelSession(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("sessionId") Long sessionId,
                                RedirectAttributes redirectAttributes) {
        try {
            User student = userService.findByUsername(userDetails.getUsername());

            if (student.getRole() != 1) { // Ensure student role
                redirectAttributes.addFlashAttribute("error", "Access denied");
            }

            // Verify session belongs to student
            Session session = sessionService.getStudentSessions(student.getId()).stream()
                    .filter(s -> s.getId().equals(sessionId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Session not found"));
            
            // Verify session is upcoming
            if (!session.getDate().isAfter(LocalDate.now())) {
                throw new RuntimeException("Cannot cancel past or today's sessions.");
            }
            sessionService.cancelSession(sessionId);
            redirectAttributes.addFlashAttribute("success", "Session canceled successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student";
    }
}