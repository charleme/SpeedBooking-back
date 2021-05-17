package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.model.GenreBook;

import java.util.Collection;

public class GenreInformation {
    private Long idGenre;

    private String nameGenre;

    public GenreInformation() {
    }

    public GenreInformation(Long idGenre, String nameGenre) {
        this.idGenre = idGenre;
        this.nameGenre = nameGenre;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
}
