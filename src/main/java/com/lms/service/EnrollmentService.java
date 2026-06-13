package com.lms.service;

public interface EnrollmentService {
    void enrollUser(Long userId, Long courseId);
    void unenrollUser(Long userId, Long courseId);
    boolean isEnrolled(Long userId, Long courseId);
}