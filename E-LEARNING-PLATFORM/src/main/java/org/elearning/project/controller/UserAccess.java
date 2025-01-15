package org.elearning.project.controller;


import org.elearning.project.model.User;
import org.elearning.project.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
public class UserAccess {


    private final UserManagementService userManagementService;

    @Autowired
    public UserAccess(UserManagementService userManagementService){
        this.userManagementService = userManagementService;
    }



    @PostMapping("/login")
    @ResponseBody
    public User login(@RequestBody User user){
        User user1 =  userManagementService.LoginUser(user.getEmail(),user.getPassword());
        return user1 != null ? user1: new User("password or email is wrong","password or email is wrong");
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody  User user){
        return userManagementService.registerUser(user)+" account saved";
    }


}
