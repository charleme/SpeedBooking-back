package fr.speedbooking.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

