package org.elearning.project.service;


import jakarta.transaction.Transactional;
import org.elearning.project.model.Course;
import org.elearning.project.model.Lesson;
import org.elearning.project.model.Subscription;
import org.elearning.project.model.User;
import org.elearning.project.repository.CourseRepository;
import org.elearning.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public List<Course> getCoursesListByPage(Pageable pageable) {
        List<Course> courseList = courseRepository.findAll(pageable).toList();
        courseList.forEach(course ->
                course.setTeacher(courseRepository.findTeacherByCourse(course.getId())));
        return courseList;
    }

    public List<Course> getCoursesListByPageRating(Pageable pageable) {

        List<Course> courseList = courseRepository.findAll(pageable).stream()
                .filter(course -> course.getRating() > 3.0)
                .toList();
        courseList.forEach(course -> course.setTeacher(courseRepository.findTeacherByCourse(course.getId())));
        return courseList;
    }

    public Course getCourse(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByTitle(name);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void addMoreCourse(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    public List<Course> searchCourses(String query) {
        return courseRepository.searchCourses(query);
    }

    @Transactional
    public void updateCourse(Course course) {
        Course course1 = courseRepository.findById(course.getId()).orElse(null);
        if (course1 != null) {
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

    public void deleteCourse(Course course) {
        courseRepository.deleteById(course.getId());
    }

    @Transactional
    public List<Lesson> getLesonList(Course course) {
        Course course1 = courseRepository.findById(course.getId()).orElse(null);
        return course1.getLessonList();
    }

    @Transactional
    public void updateLesson(String lesson, String course, User user){

        System.out.println("Updating !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Updating !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Updating !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Updating !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        User user1 = userRepository.findById(user.getId()).orElse(null);
        if(user1 != null){
            Subscription subscription = user1.getSubscriptionList().stream()
                    .filter(subscription1 -> subscription1.getCourse().getId() == Integer.parseInt(lesson))
                    .findFirst()
                    .orElse(null);
            Course course1 = subscription.getCourse();
            course1.getLessonList().stream()
                    .filter(lesson2 -> lesson2.getId() == Integer.parseInt(lesson))
                    .findFirst().ifPresent(lesson1 -> lesson1.setCompleted(true));

        }
    }


}
