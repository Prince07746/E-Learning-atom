package org.elearning.project.service;

import org.elearning.project.model.User;
import org.elearning.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {


    private final UserRepository userRepository;

    @Autowired
    public UserManagementService(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    // Register User by checking if email exist or not --------------
    public boolean registerUser(User user){
        // get the user email check for it in the database
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }
    //----------------------------------------------------------------



    // for user login ------------------------------------------------
    public User LoginUser(String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }
    //----------------------------------------------------------------

}
