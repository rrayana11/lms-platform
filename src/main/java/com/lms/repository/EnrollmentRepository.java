package com.lms.repository;

import com.lms.domain.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Найти все записи пользователя
    List<Enrollment> findByUserId(Long userId);

    // Найти запись по пользователю и курсу
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    // Проверить, записан ли пользователь на курс
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    // Удалить запись
    void deleteByUserIdAndCourseId(Long userId, Long courseId);
}