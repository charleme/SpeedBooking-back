package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
