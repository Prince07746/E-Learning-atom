package org.elearning.project.service;


import jakarta.transaction.Transactional;
import org.elearning.project.model.*;
import org.elearning.project.repository.CourseRepository;
import org.elearning.project.repository.SubscriptionRepository;
import org.elearning.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserCourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public UserCourseService(CourseRepository courseRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    // Get list of all courses (for both subscribed and non-subscribed users)
    @Transactional
    public List<Course> getCoursesListByPage(Pageable pageable, User user) {
        List<Course> courseList = courseRepository.findAll(pageable).toList();
        if (user != null) {
            // For subscribed users, mark which courses are available to them
            courseList.forEach(course -> {
                Subscription subscription = user.getSubscriptionList().stream()
                        .filter(sub -> sub.getCourse().getId() == course.getId())
                        .findFirst()
                        .orElse(null);
            });
        }
        courseList.forEach(course ->
                course.setTeacher(courseRepository.findTeacherByCourse(course.getId()))
        );
        return courseList;
    }


    public List<Course> getCoursesListByPageRating(Pageable pageable) {
        List<Course> courseList = courseRepository.findAll(pageable).stream()
                .filter(course -> course.getRating() > 3.0)
                .toList();
        courseList.forEach(course -> course.setTeacher(courseRepository.findTeacherByCourse(course.getId())));
        return courseList;
    }

    @Transactional
    public List<Course> getCourses(User user) {
        User user1 = subscriptionRepository.findByIdWithSubscriptions(user.getId()).orElse(null);
        if(user1 == null) return Collections.emptyList();
        return user1.getSubscriptionList().
                stream().map(Subscription::getCourse).toList();
    }


    // Get course by ID (both for subscribed and non-subscribed users)
    public Course getCourse(int courseId, User user) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null && user != null) {
            // Mark if the user is subscribed to the course
            user.getSubscriptionList().stream()
                    .filter(sub -> sub.getCourse().getId() == courseId)
                    .findFirst().ifPresent(subscription -> course.setStatus(StatusCourse.valueOf("COMPLETED")));
        }
        return course;
    }




    // Add a new course (admin/teacher only, non-subscriber)
    public void addCourse(Course course) {
        courseRepository.save(course);
    }



    // Update a course (admin/teacher only, non-subscriber)
    @Transactional
    public void updateCourse(Course course) {
        Course existingCourse = courseRepository.findById(course.getId()).orElse(null);
        if (existingCourse != null) {
            existingCourse.setTeacher(course.getTeacher());
            existingCourse.setTitle(course.getTitle());
            existingCourse.setCategory(course.getCategory());
            existingCourse.setDate(course.getDate());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setPrice(course.getPrice());
            existingCourse.setDuration(course.getDuration());
            existingCourse.setImage(course.getImage());
            existingCourse.setLevel(course.getLevel());
            existingCourse.setRating(course.getRating());
            existingCourse.setStatus(course.getStatus());
        }
    }


    // Delete a course (admin/teacher only, non-subscriber)
    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
    }


    // Get lessons for a specific course (for both subscribed and non-subscribed users)
    @Transactional
    public List<Lesson> getLessonList(int courseId, User user) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            if (user != null) {
                // For subscribed users, only show lessons for their course
                Subscription subscription = user.getSubscriptionList().stream()
                        .filter(sub -> sub.getCourse().getId() == courseId)
                        .findFirst()
                        .orElse(null);
                if (subscription != null) {
                    return course.getLessonList();
                } else {
                    return List.of(); // No access to lessons if not subscribed
                }
            } else {
                // Non-subscribed users can see lesson previews
                return course.getLessonList();
            }
        }
        return List.of(); // No lessons available if the course doesn't exist
    }


    // Update lesson completion status for subscribed users
    @Transactional
    public void updateLessonCompletion(int lessonId, int courseId, User user) {
        User user1 = userRepository.findById(user.getId()).orElse(null);
        if (user1 != null) {
            Subscription subscription = user1.getSubscriptionList().stream()
                    .filter(sub -> sub.getCourse().getId() == courseId)
                    .findFirst()
                    .orElse(null);
            if (subscription != null) {
                Course course1 = subscription.getCourse();
                course1.getLessonList().stream()
                        .filter(lesson -> lesson.getId() == lessonId)
                        .findFirst()
                        .ifPresent(lesson -> lesson.setCompleted(true));
            }
        }
    }


    // Delete a lesson (admin/teacher only, non-subscriber)
    public void deleteLesson(int lessonId, int courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            Lesson lesson = course.getLessonList().stream()
                    .filter(l -> l.getId() == lessonId)
                    .findFirst()
                    .orElse(null);
            if (lesson != null) {
                course.getLessonList().remove(lesson);
            }
        }
    }


    // Get list of certificates for a user (both for subscribed and non-subscribed users)
    @Transactional
    public List<Certificate> getCertificates(User user) {
        User user1 = subscriptionRepository.findByIdWithSubscriptions(user.getId()).orElse(null);
        if(user1 == null) return Collections.emptyList();
        return user1.getCertificateList();
    }


    // Issue a certificate (for completed course)
    @Transactional
    public void issueCertificate(User user, Course course) {
        if (user != null && course != null) {
            Certificate certificate = new Certificate();
            certificate.setUser(user);
            certificate.setCourse(course);
            certificate.setIssueDate((Timestamp) new Date());
            user.getCertificateList().add(certificate);
        }
    }


    // Delete a certificate for a user
    @Transactional
    public void deleteCertificate(User user, int certificateId) {
        Certificate certificate = user.getCertificateList().stream()
                .filter(cert -> cert.getId() == certificateId)
                .findFirst()
                .orElse(null);
        if (certificate != null) {
            user.getCertificateList().remove(certificate);
        }
    }


}
