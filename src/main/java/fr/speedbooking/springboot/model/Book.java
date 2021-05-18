package fr.speedbooking.springboot.model;

import fr.speedbooking.springboot.front.BookInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idBook;

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

    @OneToMany(mappedBy = "idBook", fetch=FetchType.EAGER)
    private Set<UserBook> readers;

    @OneToMany(mappedBy = "idBook", fetch=FetchType.EAGER)
    private Set<GenreBook> bookGenres;

    public Book() {
    }

    public Book(String titleBook, String language, String imageBook, String summaryBook, String firstChapter,
                String audienceTag, String links, User author, Set<UserBook> readers, Set<GenreBook> bookGenres) {
        this.titleBook = titleBook;
        this.language = language;
        this.imageBook = imageBook;
        this.summaryBook = summaryBook;
        this.firstChapter = firstChapter;
        this.audienceTag = audienceTag;
        this.links = links;
        this.author = author;
        this.readers = readers;
        this.bookGenres = bookGenres;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }
    
    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

    public Set<UserBook> getReaders() {
        return readers;
    }

    public void setReaders(Set<UserBook> readers) {
        this.readers = readers;
    }

    public Set<GenreBook> getBookGenres() {
        return bookGenres;
    }

    public void setBookGenres(Set<GenreBook> bookGenres) {
        this.bookGenres = bookGenres;
    }

    public BookInformation parseToBookInformation(){
        Long idAuthor = this.author.getIdUser();
        return new BookInformation(this.idBook, this.titleBook, this.language, this.imageBook, this.summaryBook, this.firstChapter, this.audienceTag, this.links, idAuthor);
    }

    public Map<String, Integer> getMappedAudienceTag(){
        try {
            return new ObjectMapper().readValue(this.audienceTag, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void setAudienceTag(Map<String, Integer> audienceTag){
        try {
            new ObjectMapper().writeValueAsString(audienceTag);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}