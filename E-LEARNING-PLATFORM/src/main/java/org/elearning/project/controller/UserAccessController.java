package org.elearning.project.controller;


import jakarta.transaction.Transactional;
import org.elearning.project.DTO.DashBoardData;
import org.elearning.project.model.*;
import org.elearning.project.service.CourseService;
import org.elearning.project.service.UserLearningService;
import org.elearning.project.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserAccessController {

    private final UserManagementService userManagementService;
    private final UserLearningService userLearningService;
    private final CourseService courseService;
    private User userLogin = null;

    @Autowired
    public UserAccessController(UserManagementService userManagementService,
                                UserLearningService userLearningService,
                                CourseService courseService) {

        this.userManagementService = userManagementService;
        this.userLearningService = userLearningService;
        this.courseService = courseService;
    }


    // home
    @GetMapping("/")
    public String home(Model model) {

        Pageable pageable = PageRequest.of(0,10);
        Pageable pageableByRating = PageRequest.of(0,10);

        List<Course> courseListLatest = courseService.getCoursesListByPage(pageable);

        List<Course> courseListRating = courseService.getCoursesListByPageRating(pageableByRating);



        if (userLogin != null) {
            model.addAttribute("authentication", "Logout");
            model.addAttribute("authenticationLink", "logout");
            model.addAttribute("dashboard", "Dashboard");
            model.addAttribute("dashboardLink", "/dashboard/" + userLogin.getId());
            model.addAttribute("courseListLates",courseListLatest);
            model.addAttribute("courseListRating",courseListRating);
        } else {
            model.addAttribute("authentication", "Login");
            model.addAttribute("authenticationLink", "login");
            model.addAttribute("dashboard", "");
            model.addAttribute("dashboardLink", "");
            model.addAttribute("courseListLates",courseListLatest);
            model.addAttribute("courseListRating",courseListRating);
        }

        return "index";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("SignupCheck", "");
        model.addAttribute("loginCheck", "");
        return "loginAndSignup";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("SignupCheck", "");
        model.addAttribute("loginCheck", "");
        return "loginAndSignup";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        User user1 = userManagementService.loginUser(user.getEmail(), user.getPassword());
        model.addAttribute("SignupCheck", "");
        if (user1 != null) {
            userLogin = user1;
            return "redirect:/";
        } else {
            model.addAttribute("loginCheck", "password or email is wrong");
            return "loginAndSignup";
        }
    }


    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {

        boolean isUser = userManagementService.registerUser(user);
        model.addAttribute("loginCheck", "");
        model.addAttribute("SignupCheck", "");
        if (!isUser) {
            model.addAttribute("SignupCheck", "email exist");
        }
        return "loginAndSignup";
    }

    @GetMapping("/logout")
    public String logout() {
        userLogin = null;
        return "redirect:/";
    }


    //    dashboard
    @GetMapping("/dashboard/{id}")
    public String dashboard(@PathVariable(name = "id") int id, Model model) {
        String link = "redirect:/login"; // Default to redirect if user is not authenticated

        if (userLogin != null) {
            if (userLogin.getRole().equals(Enum.valueOf(Role.class, "STUDENT")) && userLogin.getId() == id) {


                model.addAttribute("Courses", userLearningService.getCourses(userLogin));
                model.addAttribute("Certificates", userLearningService.getCertificateList(userLogin));
                model.addAttribute("userinfo", userLogin);
                link = "dashboardStudent";

            } else if (userLogin.getRole().equals(Enum.valueOf(Role.class, "TEACHER")) && userLogin.getId() == id) {

                model.addAttribute("Courses", userLearningService.getCourses(userLogin));
                model.addAttribute("Certificates", userLearningService.getCertificateList(userLogin));
                model.addAttribute("userinfo", userLogin);
                link = "dashboardStudent";

            }

        }

        return link;
    }


}
