package fr.speedbooking.springboot.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.UserInformation;
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
	public UserInformation findUserByEmailAndPassword(@RequestBody String email, @RequestBody String password){
		String modifiedPassword = User.PREFIX + password + User.SUFIX;
		List<User> listOfUsers = userRepository.findAll();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		for (User user : listOfUsers) {
			if (passwordEncoder.matches(modifiedPassword, user.getPassword()) && email.equals(user.getEmail())) {
				return user.parseToUserInformation();
			}
		}
	    throw new RessourceNotFoundException("Your email or password is not correct ! Try again");
	}

}