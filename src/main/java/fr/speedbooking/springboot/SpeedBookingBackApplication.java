package fr.speedbooking.springboot;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import fr.speedbooking.springboot.model.Genre;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.GenreRepository;
import fr.speedbooking.springboot.repository.UserRepository;

@SpringBootApplication
public class SpeedBookingBackApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpeedBookingBackApplication.class, args);
		UserRepository userDao = ctx.getBean(UserRepository.class);
		List<User> genres = userDao.findAll();
		for(User u : genres) {
			System.out.println("Team : "+ u.getUsername());
		}
		
	}

}
