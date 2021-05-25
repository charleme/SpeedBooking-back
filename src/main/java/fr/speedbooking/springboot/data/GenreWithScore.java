package fr.speedbooking.springboot.data;

import fr.speedbooking.springboot.front.GenreInformationWithScore;
import fr.speedbooking.springboot.model.Genre;

public class GenreWithScore {
    int score;
    Genre genre;

    public GenreWithScore() {
    }

    public GenreWithScore(int score, Genre genre) {
        this.score = score;
        this.genre = genre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public GenreInformationWithScore parseToGenreInformation(){
        return new GenreInformationWithScore(this.score, this.genre.parseToGenreInformation());
    }
}
