package com.lms.repository;

import com.lms.domain.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findByCourseIdOrderByOrderIndex(Long courseId);

    @Query("SELECT s FROM Section s LEFT JOIN FETCH s.lessons WHERE s.course.id = :courseId ORDER BY s.orderIndex")
    List<Section> findByCourseIdWithLessons(@Param("courseId") Long courseId);
}