package fr.speedbooking.springboot;

import fr.speedbooking.springboot.controller.AuthenticationController;
import fr.speedbooking.springboot.controller.BookController;
import fr.speedbooking.springboot.controller.UserController;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpeedBookingBackApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpeedBookingBackApplication.class, args);
	}
}