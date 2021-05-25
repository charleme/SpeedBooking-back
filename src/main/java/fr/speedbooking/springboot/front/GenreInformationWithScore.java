package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.data.GenreWithScore;

public class GenreInformationWithScore {
    int score;
    GenreInformation genre;

    public GenreInformationWithScore() {
    }

    public GenreInformationWithScore(int score, GenreInformation genre) {
        this.score = score;
        this.genre = genre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GenreInformation getGenre() {
        return genre;
    }

    public void setGenre(GenreInformation genre) {
        this.genre = genre;
    }

    public GenreWithScore parseToGenreWithScore(){
        return new GenreWithScore(this.score, this.genre.parseToGenre());
    }
}
