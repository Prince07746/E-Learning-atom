package org.elearning.project.repository;

import org.elearning.project.model.Course;
import org.elearning.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Course findByTitle(String title);

    @Query("SELECT c FROM Course c JOIN FETCH c.teacher")
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.teacher ORDER BY c.rating DESC")
    Page<Course> findAllByRating(Pageable pageable);

    @Query("SELECT c.teacher FROM Course c WHERE c.id = :courseId")
    User findTeacherByCourse(@Param("courseId") int courseId);

    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Course> searchCourses(@Param("query") String query);


}

