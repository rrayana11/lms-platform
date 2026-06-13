package com.lms.service;

import com.lms.domain.entity.Course;
import com.lms.domain.entity.Enrollment;
import com.lms.domain.entity.User;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 UserRepository userRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public void enrollUser(Long userId, Long courseId) {
        // Проверяем, не записан ли уже
        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new RuntimeException("Вы уже записаны на этот курс");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        Enrollment enrollment = new Enrollment(user, course);
        enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void unenrollUser(Long userId, Long courseId) {
        enrollmentRepository.deleteByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public boolean isEnrolled(Long userId, Long courseId) {
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }
}
