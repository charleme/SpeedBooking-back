package fr.speedbooking.springboot.front;

public class GenreWithScore {

    private Long idGenre;
    private String nameGenre;
    private int score;

    public GenreWithScore() {
    }

    public GenreWithScore(Long idGenre, String nameGenre, int score) {
        this.idGenre = idGenre;
        this.nameGenre = nameGenre;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
