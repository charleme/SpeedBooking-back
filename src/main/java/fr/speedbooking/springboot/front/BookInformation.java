package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;
import fr.speedbooking.springboot.repository.UserRepository;

public class BookInformation {
    private int idBook;

    private String titleBook;

    private String language;

    private String imageBook;

    private String summaryBook;

    private String firstChapter;

    private String audienceTag;

    private String links;

    private Long id_author;

    public BookInformation() {
    }

    public BookInformation(int idBook, String titleBook, String language, String imageBook, String summaryBook, String firstChapter, String audienceTag, String links, Long id_author) {
        this.idBook = idBook;
        this.titleBook = titleBook;
        this.language = language;
        this.imageBook = imageBook;
        this.summaryBook = summaryBook;
        this.firstChapter = firstChapter;
        this.audienceTag = audienceTag;
        this.links = links;
        this.id_author = id_author;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImageBook() {
        return imageBook;
    }

    public void setImageBook(String imageBook) {
        this.imageBook = imageBook;
    }

    public String getSummaryBook() {
        return summaryBook;
    }

    public void setSummaryBook(String summaryBook) {
        this.summaryBook = summaryBook;
    }

    public String getFirstChapter() {
        return firstChapter;
    }

    public void setFirstChapter(String firstChapter) {
        this.firstChapter = firstChapter;
    }

    public String getAudienceTag() {
        return audienceTag;
    }

    public void setAudienceTag(String audienceTag) {
        this.audienceTag = audienceTag;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public Long getId_author() {
        return id_author;
    }

    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    public Book parseToBook(){
        return new Book(this.titleBook, this.language, this.imageBook, this.summaryBook, this.firstChapter,
                this.audienceTag, this.links, null, null, null);
    }
}
