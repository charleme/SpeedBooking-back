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
		GenreRepository genreDao = ctx.getBean(GenreRepository.class);
		UserRepository userDao = ctx.getBean(UserRepository.class);
		userDao.save(new User("em", "em@llml", ";dlczeùzzù", new Timestamp(354442), "testt"));
		//teamDao.save(new Team("OM"));
		//teamDao.save(new Team("OL"));
		//teamDao.save(new Team("PSG"));
		List<Genre> genres = genreDao.findAll();
		for(Genre g : genres) {
			System.out.println("Team : "+ g.getName()); 
		} 
		
	}

}
