package fr.speedbooking.springboot;

import fr.speedbooking.springboot.controller.BookController;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpeedBookingBackApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpeedBookingBackApplication.class, args);
		BookController books = ctx.getBean(BookController.class);
		UserRepository users = ctx.getBean(UserRepository.class);
	}
}
