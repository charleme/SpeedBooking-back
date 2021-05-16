package fr.speedbooking.springboot.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


@Entity
@Table(name = "book")
public class Book implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;

	@ManyToOne
	@JoinColumn(name = "id_author")
	private User author;
    
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

    public Book() {

    }
    public Book(String title, String image, String summary, String first_chapter, String audienceTags, String links) {
    	this.bookTitle = title;
    	this.bookImage = image;
    	this.summary = summary;
    	this.firstChapter = first_chapter;
    	this.audienceTag = audienceTags;
    	this.links = links;
    }
    
    @Id
    public Long getIdBook() {
        return idBook;
    }
    
    public void setIdBook(Long idBook) {
    	this.idBook = idBook;
    }
    
    public User getAuthor() {
    	return author;
    }
    
    public void setAuthor(User author) {
    	this.author = author;
    }
    
    public String getTitle() {
    	return bookTitle;
    }
    
    public void setTitle(String title) {
    	this.bookTitle = title;
    }
    
    public String getImage() {
    	return bookImage;
    }
    
    public void setBookImage(String bookImage) {
    	this.bookImage = bookImage;
    }
    
    public String getSummary() {
    	return this.summary;
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
    
    public String getAudienceTags() {
    	return audienceTag;
    }
    
    public void setAudienceTags(String tags) {
    	this.audienceTag = tags;
    }
    
    public String getLinks() {
    	return links;
    }
    
    public void setLinks(String links) {
    	this.links = links;
    }
    
}