package com.lms.service;

import com.lms.domain.entity.Course;
import com.lms.domain.entity.Enrollment;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Course not found with id: " + id));
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = getCourseById(id);
        existing.setTitle(course.getTitle());
        existing.setDescription(course.getDescription());
        return courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByUserId(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getAvailableCoursesByUserId(Long userId) {
        List<Course> allCourses = courseRepository.findAll();
        List<Course> enrolledCourses = getCoursesByUserId(userId);

        return allCourses.stream()
                .filter(course -> enrolledCourses.stream()
                        .noneMatch(enrolled -> enrolled.getId().equals(course.getId())))
                .collect(Collectors.toList());
    }
}