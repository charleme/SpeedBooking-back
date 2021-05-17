package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping("/users")
    public List<UserInformation> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> user.parseToUserInformation())
                .collect(Collectors.toList());
    }

    @GetMapping("/findUser/{id}")
    public ResponseEntity<UserInformation> getUserById(@PathVariable Long id){
        UserInformation user = userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + id)).parseToUserInformation();
        return ResponseEntity.ok(user);
    }

    //add user to the database
    @PostMapping("/addUser")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
