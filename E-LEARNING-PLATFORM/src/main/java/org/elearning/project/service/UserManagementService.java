package org.elearning.project.service;

import org.elearning.project.model.User;
import org.elearning.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {


    private final UserRepository userRepository;

    @Autowired
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Register User by checking if email exist or not --------------
    public boolean registerUser(User user) {
        // get the user email check for it in the database
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    //----------------------------------------------------------------


    // for user login ------------------------------------------------
    public User loginUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    //----------------------------------------------------------------


    // Get List of Users --------------------------------------------
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    // get List of Users by name -------------------------------------
    public User getUserByName(String name){
        return userRepository.findByName(name);
    }

    //-----------------------------------------------------------------


    // get List of Users by name -------------------------------------
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    //-----------------------------------------------------------------


    // remove user ---------------------------------------------------
    public void deleteUser(User user) {
       userRepository.delete(user);
    }
    // ---------------------------------------------------------------


    // get User and id ------------------------------------------------------

    public User getUser(String email){
       return userRepository.findByEmail(email); // return of type User
    }

    public User getUser(int id){
       return userRepository.findById(id).orElse(null); // return of type User
    }
    // --------------------------------------------------------------



    // edit user -----------------------------------------------------
     public void editUser(User user){
        User user1 = getUser(user.getEmail());

        user.setCreated_at(user1.getCreated_at());
        user.setId(user1.getId());

        deleteUser(user1);

        userRepository.save(new User(
                user1.getId(),
                user.getName(),user.getEmail(),
                user.getPassword(),user.getRole())
        );
     }

    //-----------------------------------------------------------------
}
