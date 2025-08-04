package com.example.project.repository;

import com.example.project.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsByTutorIdAndDateAndTimeSlot(Long tutorId, LocalDate date, String timeSlot);

    List<Session> findByTutorId(Long tutorId);

    List<Session> findByTutorIdAndDateGreaterThanEqual(Long tutorId, LocalDate fromDate);

    List<Session> findByStudentId(Long studentId);

    boolean existsByStudentIdAndDateAndTimeSlot(Long studentId, LocalDate date, String timeSlot);
}
