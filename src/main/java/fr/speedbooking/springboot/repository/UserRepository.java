package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.front.ReadBookWithProgress;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT b FROM  Book b " +
            "JOIN b.readers ub " +
            "JOIN ub.idUser u " +
            "WHERE u.idUser = :userId")
    List<Book> findReadBooksByUserId(@Param("userId") Long id);

    @Query("SELECT b FROM  Book b " +
            "JOIN b.author u " +
            "WHERE u.idUser = :userId")
    List<Book> findWrittenBooksByUserId(@Param("userId") Long id);

    @Query("SELECT ub FROM UserBook ub " +
            "JOIN ub.idUser u " +
            "WHERE u.idUser = :userId")
    List<UserBook> findUserBooksByUserId(@Param("userId") Long id);

    @Query("SELECT " +
            "new fr.speedbooking.springboot.front.ReadBookWithProgress(ub.progress, b) " +
            "FROM UserBook ub " +
            "JOIN ub.idUser u " +
            "JOIN ub.idBook b " +
            "WHERE u.idUser = :userId")
    List<ReadBookWithProgress> getReadBooksWithProgress(@Param("userId") Long id);
    
    @Query("SELECT DISTINCT b FROM Book b, UserBook ub "
    		+ "WHERE b.idBook NOT IN (SELECT ub.idBook FROM UserBook ub WHERE ub.idUser.idUser= :userId) "
    		+ "AND b.idBook IN (SELECT gb.idBook"
    		+ " FROM GenreBook gb, Genre g "
    		+ "WHERE g.nameGenre IN (:favs) "
    		+ "AND gb.idGenre = g.idGenre)")
    List<Book> algo1(@Param("userId") Long id, @Param("favs") List<String> favs, Pageable pageable);
    
    @Query("SELECT DISTINCT b FROM Book b, UserBook ub "
    		+ "WHERE b.idBook NOT IN (SELECT ub.idBook FROM UserBook ub WHERE ub.idUser.idUser= :userId) "
    		+ "AND b NOT IN (:algo1)")
    List<Book> eligible_algo2(@Param("userId") Long id, @Param("algo1") List<Book> algo1);
    
    @Query("SELECT DISTINCT b FROM Book b, UserBook ub "
    		+ "WHERE b.idBook NOT IN (SELECT ub.idBook FROM UserBook ub WHERE ub.idUser.idUser= :userId)")
    List<Book> alt_eligible_algo2(@Param("userId") Long id);
    
    @Query("SELECT DISTINCT b FROM Book b, UserBook ub "
    		+ "WHERE b.idBook NOT IN (SELECT ub.idBook FROM UserBook ub WHERE ub.idUser.idUser= :userId) "
    		+ "AND b NOT IN (:TL) ORDER BY RAND()")
    List<Book> findRandomBooks_TL(@Param("userId") Long id, @Param("TL") List<Book> TL, Pageable pageable);
    
	//@Query("select user from User user where user.email = :email and user.password = :mdp")
	//public User findByEmailAndPassword(@Param("email")String login, @Param("mdp") String mdp);
}
