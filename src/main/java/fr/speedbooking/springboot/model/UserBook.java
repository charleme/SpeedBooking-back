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
    private User idUser;
	
	@ManyToOne
    @JoinColumn(name = "book_id_book")
    private Book idBook;
	
	@Column (name = "progress")
	private Integer progress;
	
	public UserBook() {
		
	}
	
	public UserBook(User idUser, Book idBook, int progress) {
		this.idUser = idUser;
		this.idBook = idBook;
		this.progress = progress;
	}

	public Long getIdUserBook() {
		return idUserBook;
	}
	
	public void setIdUserBook(Long id) {
		this.idUserBook = id;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}

	public Book getIdBook() {
		return idBook;
	}

	public void setIdBook(Book idBook) {
		this.idBook = idBook;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}