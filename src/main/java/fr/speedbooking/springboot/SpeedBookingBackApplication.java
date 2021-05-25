package fr.speedbooking.springboot;

import fr.speedbooking.springboot.controller.AuthenticationController;
import fr.speedbooking.springboot.controller.BookController;
import fr.speedbooking.springboot.controller.UserController;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpeedBookingBackApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpeedBookingBackApplication.class, args);
		UserRepository users = ctx.getBean(UserRepository.class);
		AuthenticationController authenticationController = ctx.getBean(AuthenticationController.class);
		System.out.println(authenticationController.findUserByEmailAndPassword("ptit.loukoum@fakemail.com", "SoyElLoukoum").getUsername());
	}
}