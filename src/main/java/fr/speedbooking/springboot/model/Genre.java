package fr.speedbooking.springboot.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "genre")
public class Genre implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;

    @Column(name = "name_genre", length = 45)
    private String nameGenre;
    
    public Genre() {
    	
    }
    
    public Genre(String name) {
    	this.nameGenre = name;
    }
    
    @Id
    public Long getIdGenre() {
    	return idGenre;
    }
    
    public void setIdGenre(Long id) {
    	this.idGenre = id;
    }
    
    public String getNameGenre() {
    	return nameGenre;
    }
    
    public void setNameGenre(String name) {
    	this.nameGenre = name;
    }
}
