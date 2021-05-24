package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.model.GenreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreBookRepository extends JpaRepository<GenreBook, Long> {
}
