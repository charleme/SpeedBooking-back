package fr.speedbooking.springboot.model;

import fr.speedbooking.springboot.front.BookInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Entity
public class Book implements Serializable{
	public static final int NUMBER_MAJOR_AUDIENCE_TAGS_ANALYSED = 3;
    public static final int AUDIENCE_TAG_INCREMENT = 1;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idBook;

    @Column
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

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
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
        return new BookInformation(this.idBook, this.titleBook, this.language, this.imageBook, this.summaryBook,
                this.firstChapter, this.getMappedAudienceTag(), this.getMappedLinks(), idAuthor);
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
            this.audienceTag = new ObjectMapper().writeValueAsString(audienceTag);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getMappedLinks(){
        try {
            return new ObjectMapper().readValue(this.links, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void setLinks(Map<String, String> links){
        try {
            this.links = new ObjectMapper().writeValueAsString(links);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> getMajorAudienceTags(){
        List<String> majorTags = new ArrayList<>();

        Map<String, Integer> tags = this.getMappedAudienceTag();

        Comparator<String> valueComparator = new Comparator<String>() {
            public int compare(String k1, String k2) {
                Integer v1 = tags.get(k1);
                Integer v2 = tags.get(k2);
                int compare = v2.compareTo(v1); //DESC
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }

            }
        };

        TreeMap<String,Integer> sortedByValues = new TreeMap<>(valueComparator);

        sortedByValues.putAll(tags);

        for (int i = 0; i < Math.min(NUMBER_MAJOR_AUDIENCE_TAGS_ANALYSED, tags.size()); i++) {
            majorTags.add(sortedByValues.pollFirstEntry().getKey());
        }

        return majorTags;
    }

    public void applyChangeAlgorithm(boolean like, List<String> userPreferredGenres){
        int changeValue = (like) ? AUDIENCE_TAG_INCREMENT : -AUDIENCE_TAG_INCREMENT;

        Map<String, Integer> mappedAudienceTag = this.getMappedAudienceTag();

        for (String genre :
                userPreferredGenres) {
            mappedAudienceTag.put(genre, mappedAudienceTag.get(genre)+changeValue);
        }

        this.setAudienceTag(mappedAudienceTag);
    }
}