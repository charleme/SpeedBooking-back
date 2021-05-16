package fr.speedbooking.springboot.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private int idBook;

    @Column(name = "title_book")
    private String titleBook;
    
    @Column(name = "language")
    private String language;

    @Column(name = "image_book")
    private String imageBook;

    @Column(name = "summary_book")
    private String summaryBook;

    @Column(name = "first_chapter")
    private String firstChapter;

    @Column(name = "audience_tag")
    private String audienceTag;

    @Column(name = "links")
    private String links;

    @ManyToOne
    @JoinColumn(name="idAuthor")
    private User author;

    public Book() {
    }

    public Book(String bookTitle, String language, String bookImage, String summary, String firstChapter, String audienceTag, String links, User author) {
        this.titleBook = bookTitle;
        this.language = language;
        this.imageBook = bookImage;
        this.summaryBook = summary;
        this.firstChapter = firstChapter;
        this.audienceTag = audienceTag;
        this.links = links;
        this.author = author;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
    
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBookTitle() {
        return titleBook;
    }

    public void setBookTitle(String bookTitle) {
        this.titleBook = bookTitle;
    }

    public String getBookImage() {
        return imageBook;
    }

    public void setBookImage(String bookImage) {
        this.imageBook = bookImage;
    }

    public String getSummary() {
        return summaryBook;
    }

    public void setSummary(String summary) {
        this.summaryBook = summary;
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
}