package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreBookRepository extends JpaRepository<GenreBook, Long> {
    @Query("SELECT gb.score FROM GenreBook gb " +
            "JOIN gb.idBook b " +
            "JOIN gb.idGenre g " +
            "WHERE b.idBook = :bookId " +
            "AND g.idGenre = :genreId")
    public int getScore(@Param("genreId") Long idGenre, @Param("bookId") Long idBook);

    @Query("SELECT gb FROM GenreBook gb " +
            "JOIN gb.idBook b " +
            "JOIN gb.idGenre g " +
            "WHERE b.idBook = :bookId " +
            "AND g.idGenre = :genreId")
    public GenreBook getGenreBookByGenreIdAndBookId(@Param("genreId") Long idGenre, @Param("bookId") Long idBook);
}
