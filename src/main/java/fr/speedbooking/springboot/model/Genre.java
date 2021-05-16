package fr.speedbooking.springboot.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "genre")
public class Genre implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;

    @OneToMany(mappedBy = "idGenre")
    private Collection<GenreBook> booksAssociated;

    @Column(name = "name_genre", length = 45)
    private String nameGenre;
    
    public Genre() {
    	
    }
    
    public Genre(String name, Set<GenreBook> booksAssociated) {
    	this.nameGenre = name;
    	this.booksAssociated = booksAssociated;
    }
    

    public Long getIdGenre() {
    	return idGenre;
    }
    
    public void setIdGenre(Long id) {
    	this.idGenre = id;
    }

    public Collection<GenreBook> getBooksAssociated() {
        return booksAssociated;
    }

    public void setBooksAssociated(Collection<GenreBook> booksAssociated) {
        this.booksAssociated = booksAssociated;
    }
    
    public String getNameGenre() {
    	return nameGenre;
    }
    
    public void setNameGenre(String name) {
    	this.nameGenre = name;
    }


}
