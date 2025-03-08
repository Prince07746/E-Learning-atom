package org.elearning.project.controller;


import org.elearning.project.model.Course;
import org.elearning.project.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoursesController {

    private final UserCourseService usercourseService;

    @Autowired
    public CoursesController(UserCourseService usercourseService){
        this.usercourseService = usercourseService;
    }

    @PostMapping("/addCourse")
    @ResponseBody
   public void addCourses(@RequestBody List<Course> course){
        usercourseService.addMoreCourse(course);
        System.out.println("ok good storing of courses");
   }

    @GetMapping("/search/course")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String query) {
        List<Course> courses = usercourseService.searchCourses(query);
        return ResponseEntity.ok(courses);
    }




}
