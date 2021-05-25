package fr.speedbooking.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/connection/")
public class AuthenticationController {
	 @Autowired
	 private UserRepository userRepository;
	 
	//find user by email and password to check if the user exists
	@PostMapping("/connect")
	public User findUserByEmailAndPassword(@RequestBody String email, String password){
	     User user = userRepository.findByEmailAndPassword(email, password);
	     if (user != null) {
	    	 return user;
	     } else {
	    	 throw new RessourceNotFoundException("Your email or password is not correct ! Try again");
	     }
	}

}