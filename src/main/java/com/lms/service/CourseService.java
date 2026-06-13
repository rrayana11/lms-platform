package com.lms.service;

import com.lms.domain.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);

    // Новые методы для записей
    List<Course> getCoursesByUserId(Long userId);
    List<Course> getAvailableCoursesByUserId(Long userId);
}