package fr.speedbooking.springboot.repository;

import fr.speedbooking.springboot.data.ReadBookWithProgress;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;
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
            "new fr.speedbooking.springboot.data.ReadBookWithProgress(ub.progress, b) " +
            "FROM UserBook ub " +
            "JOIN ub.idUser u " +
            "JOIN ub.idBook b " +
            "WHERE u.idUser = :userId")
    List<ReadBookWithProgress> getReadBooksWithProgress(@Param("userId") Long id);
    
	@Query("select user from User user where user.email = :email and user.password = :mdp")
	public User findByEmailAndPassword(@Param("email")String login, @Param("mdp") String mdp);
}
