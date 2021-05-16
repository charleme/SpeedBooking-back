package fr.speedbooking.springboot;

import java.sql.Timestamp;
import java.util.List;

import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
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
		List<User> users = userDao.findAll();
		for(User u : users) {
			System.out.println("User : "+ u.getBooks());
		}

		BookRepository bookDao = ctx.getBean(BookRepository.class);
		List<Book> books = bookDao.findAll();
		for(Book u : books) {
			System.out.println("Book : "+ u.getReaders());
		}
		
	}

}
