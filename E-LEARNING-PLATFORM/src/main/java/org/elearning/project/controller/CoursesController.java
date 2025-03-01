package org.elearning.project.controller;


import org.elearning.project.model.Course;
import org.elearning.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoursesController {

    private final CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/addCourse")
    @ResponseBody
   public void addCourses(@RequestBody List<Course> course){
        courseService.addMoreCourse(course);
        System.out.println("ok good storing of courses");
   }

    @GetMapping("/search/course")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String query) {
        List<Course> courses = courseService.searchCourses(query);
        return ResponseEntity.ok(courses);
    }




}
