package fr.speedbooking.springboot.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userbook")
public class UserBook implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserBook;

	@ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
	
	@ManyToOne
    @JoinColumn(name = "id_book")
    private Book id_book;
	
	@Column (name = "user_progress")
	private int userProgress;
	
	public UserBook() {
		
	}
	
	public UserBook(User id_user, Book id_book, int userProgress) {
		this.id_user = id_user;
		this.id_book = id_book;
		this.userProgress = userProgress;
	}
	
	@Id
	public Long getIdUserBook() {
		return idUserBook;
	}
	
	public void setIdUserBook(Long id) {
		this.idUserBook = id;
	}

	public User getId_user() {
		return id_user;
	}

	public void setId_user(User idUser) {
		this.id_user = idUser;
	}

	public Book getId_book() {
		return id_book;
	}

	public void setId_book(Book idBook) {
		this.id_book = idBook;
	}

	public int getUserProgress() {
		return userProgress;
	}

	public void setUserProgress(int progress) {
		this.userProgress = progress;
	}
}