package com.lms.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @CreatedDate
    @Column(name = "enrolled_at", updatable = false)
    private LocalDateTime enrolledAt;

    @Column(name = "progress")
    private Integer progress = 0;

    @Column(name = "completed")
    private Boolean completed = false;

    public Enrollment(User user, Course course) {
        this.user = user;
        this.course = course;
        this.progress = 0;
        this.completed = false;
    }
}