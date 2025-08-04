package com.example.project.service;

import com.example.project.entity.Session;
import com.example.project.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Create a new session for a tutor (no student assigned yet).
     * Checks for duplicate sessions with same tutor, date, and time slot.
     */
    public Session createSession(Long tutorId, LocalDate date, String timeSlot) {
        boolean exists = sessionRepository.existsByTutorIdAndDateAndTimeSlot(tutorId, date, timeSlot);
        if (exists) {
            throw new RuntimeException("Session already exists for this tutor at the given date and time.");
        }

        Session session = new Session();
        session.setTutorId(tutorId);
        session.setDate(date);
        session.setTimeSlot(timeSlot);
        session.setStudentId(null); // not booked yet

        return sessionRepository.save(session);
    }

    /**
     * Delete a session by ID.
     */
    public void deleteSession(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Session not found.");
        }
        sessionRepository.deleteById(sessionId);
    }

    /**
     * Get sessions for a tutor.
     * If fromDate is provided, return sessions on or after that date.
     */
    public List<Session> getTutorSessions(Long tutorId, LocalDate fromDate) {
        List<Session> sessions = (fromDate != null)
                ? sessionRepository.findByTutorIdAndDateGreaterThanEqual(tutorId, fromDate)
                : sessionRepository.findByTutorId(tutorId);
        return sessions != null ? sessions : List.of();
    }

    /**
     * Get sessions for a student.
     */
    public List<Session> getStudentSessions(Long studentId) {
        return sessionRepository.findByStudentId(studentId);
    }

    /**
     * Book a session for a student.
     * Checks if student already has a session with same date & time slot.
     */
    public Session bookSession(Long sessionId, Long studentId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found."));

        if (session.getStudentId() != null) {
            throw new RuntimeException("Session already booked.");
        }

        boolean conflict = sessionRepository.existsByStudentIdAndDateAndTimeSlot(studentId, session.getDate(), session.getTimeSlot());
        if (conflict) {
            throw new RuntimeException("Student already has a session at this date and time.");
        }

        session.setStudentId(studentId);
        return sessionRepository.save(session);
    }

    /**
     * Cancel a session booking (remove student).
     */
    public Session cancelSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found."));

        session.setStudentId(null);
        return sessionRepository.save(session);
    }
}
