package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    @Query("SELECT ub.progress FROM UserBook ub " +
            "JOIN ub.idBook b " +
            "JOIN ub.idUser u " +
            "WHERE b.idBook = :bookId " +
            "AND u.idUser = :userId")
    public int getProgress(@Param("userId") Long idUser, @Param("bookId") Long idBook);

    @Query("SELECT ub FROM UserBook ub " +
            "JOIN ub.idBook b " +
            "JOIN ub.idUser u " +
            "WHERE b.idBook = :bookId " +
            "AND u.idUser = :userId")
    public UserBook getUserBookByUserIdAndBookId(@Param("userId") Long idUser, @Param("bookId") Long idBook);
}
