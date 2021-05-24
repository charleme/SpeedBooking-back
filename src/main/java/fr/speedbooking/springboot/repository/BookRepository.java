package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT ub FROM UserBook ub " +
            "JOIN ub.idBook b " +
            "WHERE b.idBook = :bookId")
    List<UserBook> findUserBooksByBookId(@Param("bookId") Long id);

    @Query("SELECT gb FROM GenreBook gb " +
            "JOIN gb.idBook b " +
            "WHERE b.idBook = :bookId")
    List<GenreBook> findGenreBooksByBookId(@Param("bookId") Long id);
}

