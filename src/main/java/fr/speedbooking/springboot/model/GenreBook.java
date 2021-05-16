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
    @JoinColumn(name = "book_id_book", referencedColumnName = "id_book")
    private Book book;
	
	@ManyToOne
    @JoinColumn(name = "id_genre", referencedColumnName = "id_genre")
    private Genre genre;
	
	@Column(name = "score", length = 45)
    private int score;
	
	public GenreBook() {
		
	}
	
	public GenreBook(Book book, Genre genre, int score) {
		this.book = book;
		this.genre = genre;
		this.score = score;
	}
	
	@Id
	public Long getIdGenreBook() {
		return idGenreBook;
	}
	
	public void setIdGenreBook(Long id) {
		this.idGenreBook = id;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
