package org.elearning.project.service;


import jakarta.transaction.Transactional;
import org.elearning.project.model.Course;
import org.elearning.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }


    @Transactional
    public List<Course> getCoursesListByPage(Pageable pageable){
        List<Course> courseList =  courseRepository.findAll(pageable).toList();
        courseList.forEach(course ->
                course.setTeacher(courseRepository.findTeacherByCourse(course.getId())));
        return courseList;
    }

    public List<Course> getCoursesListByPageRating(Pageable pageable){

        List<Course> courseList = courseRepository.findAll(pageable).stream()
                .filter(course -> course.getRating() > 3.0)
                .toList();
        courseList.forEach(course -> course.setTeacher(courseRepository.findTeacherByCourse(course.getId())));
        return courseList;
    }

    public Course getCourse(int courseId){
        return courseRepository.findById(courseId).orElse(null);
    }

    public Course getCourseByName(String name){
        return courseRepository.findByTitle(name);
    }

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public void addMoreCourse(List<Course> courses){
        courseRepository.saveAll(courses);
    }

    public List<Course> searchCourses(String query){
        return courseRepository.searchCourses(query);
    }

    @Transactional
    public void updateCourse(Course course){
        Course course1 = courseRepository.findById(course.getId()).orElse(null);
        if(course1 != null) {
            course1.setTeacher(course.getTeacher());
            course1.setTitle(course.getTitle());
            course1.setCategory(course.getCategory());
            course1.setDate(course.getDate());
            course1.setDescription(course.getDescription());
            course1.setPrice(course.getPrice());
            course1.setDuration(course.getDuration());
            course1.setImage(course.getImage());
            course1.setLevel(course.getLevel());
            course1.setRating(course.getRating());
            course1.setStatus(course.getStatus());
        }
    }

    public void deleteCourse(Course course){
        courseRepository.deleteById(course.getId());
    }

}
