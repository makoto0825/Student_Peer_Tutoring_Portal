package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tutor ID (foreign key to users.id)
    @Column(name = "tutor_id", nullable = false)
    private Long tutorId;

    // Student ID (foreign key to users.id, nullable)
    @Column(name = "student_id")
    private Long studentId;

    @Column(nullable = false)
    private LocalDate date;

    /**
     * Time slot stored as a single string (e.g., "9:00 AM - 10:00 AM")
     */
    @Column(name = "time_slot", nullable = false, length = 50)
    private String timeSlot;

    // Constructors
    public Session() {}

    public Session(Long tutorId, Long studentId, LocalDate date, String timeSlot) {
        this.tutorId = tutorId;
        this.studentId = studentId;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
