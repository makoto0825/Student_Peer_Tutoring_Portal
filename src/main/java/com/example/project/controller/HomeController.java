package com.example.project.controller;

import com.example.project.entity.Session;
import com.example.project.entity.User;
import com.example.project.service.SessionService;
import com.example.project.service.UserService;
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

    @Autowired
    public HomeController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
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

    /**
     * Tutor dashboard page
     * Shows upcoming and past sessions
     */
    @GetMapping("/tutor")
    public String tutorPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User tutor = userService.findByUsername(userDetails.getUsername());

        if (tutor.getRole() != 2) { // Ensure tutor role
            return "redirect:/access-denied";
        }

        Long tutorId = tutor.getId();
        LocalDate today = LocalDate.now();

        // Get all sessions for this tutor
        List<Session> sessions = sessionService.getTutorSessions(tutorId, null);

        // Upcoming sessions: today or later
        List<Session> upcomingSessions = sessions.stream()
                .filter(s -> !s.getDate().isBefore(today))
                .sorted(Comparator.comparing(Session::getDate).thenComparing(Session::getTimeSlot))
                .collect(Collectors.toList());

        // Past sessions: before today, only where studentId is not null
        List<Session> pastSessions = sessions.stream()
                .filter(s -> s.getDate().isBefore(today) && s.getStudentId() != null)
                .sorted(Comparator.comparing(Session::getDate).reversed())
                .collect(Collectors.toList());

        Map<Long, String> studentNames = new HashMap<>();
        for (Session s : sessions) {
            if (s.getStudentId() != null) {
                User student = userService.findById(s.getStudentId());
                Long sId = s.getStudentId();
                studentNames.put(sId, student.getFirstName() + " " + student.getLastName());
            }
        }

        model.addAttribute("upcomingSessions", upcomingSessions);
        model.addAttribute("pastSessions", pastSessions);
        model.addAttribute("studentNames", studentNames);
        model.addAttribute("tutorId", tutorId);

        return "tutor";
    }

    /**
     * Add a new session (Tutor)
     */
    @PostMapping("/tutor/session")
    public String createSession(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("date") String date,
                                @RequestParam("timeSlot") String timeSlot,
                                RedirectAttributes redirectAttributes) {
        try {
            User tutor = userService.findByUsername(userDetails.getUsername());
            LocalDate sessionDate = LocalDate.parse(date);
            sessionService.createSession(tutor.getId(), sessionDate, timeSlot);
            redirectAttributes.addFlashAttribute("success", "Session created successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tutor";
    }

    /**
     * Delete a session (Tutor) — only for upcoming sessions
     */
    @PostMapping("/tutor/session/delete")
    public String deleteSession(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("sessionId") Long sessionId, RedirectAttributes redirectAttributes) {
        User tutor = userService.findByUsername(userDetails.getUsername());

        if (tutor.getRole() != 2) {
            return "redirect:/access-denied";
        }

        // Verify session is upcoming before deleting
        Session session = sessionService.getTutorSessions(tutor.getId(), null).stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Session not found"));


        if (!session.getDate().isBefore(LocalDate.now())) {
            sessionService.deleteSession(sessionId);
            redirectAttributes.addFlashAttribute("success", "Session deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot delete past sessions.");
        }

        return "redirect:/tutor";
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