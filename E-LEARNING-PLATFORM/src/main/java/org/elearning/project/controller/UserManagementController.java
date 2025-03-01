package org.elearning.project.controller;

import org.elearning.project.model.User;
import org.elearning.project.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Flow.Subscription;

@Controller
@RequestMapping("/api/v1")
public class UserManagementController {


    private final UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService){
        this.userManagementService = userManagementService;
    }

    // delete user
    @GetMapping("/delete/{email}")
    public String deleteUser(@PathVariable(name = "email") String email){
        User user = userManagementService.getUser(email);
        userManagementService.deleteUser(user);
        return "User: "+user.getName()+" , deleted ";
    }


    // get the list of users
    @GetMapping("/userList")
    @ResponseBody
    public List<User> getUsers(){
        return userManagementService.getAllUsers();
    }


    // search user by name and get user by email
    @GetMapping("/searchUser")
    @ResponseBody
    public User getUsersByName(
            @RequestParam(name="name" , required = false) String name,
            @RequestParam(name="email", required = false) String email
            ){
        if(name != null){
            return userManagementService.getUserByName(name);
        }else{
            return userManagementService.getUserByEmail(email);
        }

    }





}
