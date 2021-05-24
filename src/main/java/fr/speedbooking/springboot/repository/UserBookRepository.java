package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
}
