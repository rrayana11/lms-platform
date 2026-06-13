package com.lms.controller;

import com.lms.domain.entity.Course;
import com.lms.domain.entity.User;
import com.lms.service.CourseService;
import com.lms.service.EnrollmentService;
import com.lms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PageController {

    private final CourseService courseService;
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    public PageController(CourseService courseService, UserService userService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "index";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "courses/detail";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = getCurrentUsername();
        User user = userService.getUserByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("enrolledCoursesCount", 3);
        model.addAttribute("completedCoursesCount", 1);
        model.addAttribute("studyHours", 45);
        model.addAttribute("certificatesCount", 2);

        return "profile";
    }

    @GetMapping("/my-courses")
    public String myCourses(Model model) {
        String username = getCurrentUsername();
        User user = userService.getUserByUsername(username);

        List<Course> enrolledCourses = courseService.getCoursesByUserId(user.getId());
        List<Course> availableCourses = courseService.getAvailableCoursesByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("availableCourses", availableCourses);

        return "my-courses";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String username = getCurrentUsername();
        User user = userService.getUserByUsername(username);

        if (!"INSTRUCTOR".equals(user.getRole())) {
            return "redirect:/profile";
        }

        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        model.addAttribute("totalStudents", 1234);
        model.addAttribute("totalRevenue", 45000);

        return "dashboard";
    }

    @PostMapping("/courses/{id}/enroll")
    public String enrollCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String username = getCurrentUsername();
        User user = userService.getUserByUsername(username);

        try {
            enrollmentService.enrollUser(user.getId(), id);
            redirectAttributes.addFlashAttribute("success", "Вы успешно записались на курс!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/my-courses";
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}