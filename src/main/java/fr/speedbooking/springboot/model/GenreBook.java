package fr.speedbooking.springboot.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "genrebook")
public class GenreBook implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenreBook;

	@ManyToOne
    @JoinColumn(name = "book_id_book")
    private Book book_id_book;
	
	@ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre id_genre;
	
	@Column(name = "score", length = 45)
    private int score;
	
	public GenreBook() {
		
	}
	
	public GenreBook(Book book_id_book, Genre id_genre, int score) {
		this.book_id_book = book_id_book;
		this.id_genre = id_genre;
		this.score = score;
	}
	
	@Id
	public Long getIdGenreBook() {
		return idGenreBook;
	}
	
	public void setIdGenreBook(Long id) {
		this.idGenreBook = id;
	}
	
	public Book getBook_id_book() {
		return book_id_book;
	}
	
	public void setBook_id_book(Book book) {
		this.book_id_book = book;
	}
	
	public Genre getId_genre() {
		return id_genre;
	}
	
	public void setId_genre(Genre genre) {
		this.id_genre = genre;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
