package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //add user to the database
    @PostMapping("/addUser")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
