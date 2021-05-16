package fr.speedbooking.springboot.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private int idBook;

    @Column(name = "title_book")
    private String bookTitle;

    @Column(name = "image_book")
    private String bookImage;

    @Column(name = "summary_book")
    private String summary;

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

    public Book(String bookTitle, String bookImage, String summary, String firstChapter, String audienceTag, String links, User author) {
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.summary = summary;
        this.firstChapter = firstChapter;
        this.audienceTag = audienceTag;
        this.links = links;
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
}