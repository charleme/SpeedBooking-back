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
    @JoinColumn(name = "id_book")
    private Book idBook;
	
	@ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre idGenre;
	
	@Column(name = "score", length = 45)
    private int score;
	
	public GenreBook() {
		
	}
	
	public GenreBook(Book idBook, Genre idGenre, int score) {
		this.idBook = idBook;
		this.idGenre = idGenre;
		this.score = score;
	}

	public Long getIdGenreBook() {
		return idGenreBook;
	}
	
	public void setIdGenreBook(Long id) {
		this.idGenreBook = id;
	}
	
	public Book getIdBook() {
		return idBook;
	}
	
	public void setIdBook(Book book) {
		this.idBook = book;
	}
	
	public Genre getIdGenre() {
		return idGenre;
	}
	
	public void setIdGenre(Genre genre) {
		this.idGenre = genre;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
