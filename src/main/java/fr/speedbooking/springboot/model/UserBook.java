package fr.speedbooking.springboot.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "userbook")
public class UserBook {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserBook;

	@ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;
	
	@Column (name = "user_progress")
	private int progress;
	
	public UserBook() {
		
	}
	
	public UserBook(User user, Book book, int progress) {
		this.user = user;
		this.book = book;
		this.progress = progress;
	}
	
	@Id
	public Long getIdUserBook() {
		return idUserBook;
	}
	
	public void setIdUserBook(Long id) {
		this.idUserBook = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}