package fr.speedbooking.springboot.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

public class BookInformation {
    private Long idBook;

    private String titleBook;

    private String language;

    private String imageBook;

    private String summaryBook;

    private String firstChapter;

    private Map<String, Integer> audienceTag;

    private Map<String, String> links;

    private Long id_author;

    public BookInformation() {
    }

    public BookInformation(Long idBook, String titleBook, String language, String imageBook, String summaryBook,
                           String firstChapter, Map<String, Integer> audienceTag, Map<String, String> links,
                           Long id_author) {
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

    public Map<String, Integer> getAudienceTag() {
        return audienceTag;
    }

    public void setAudienceTag(Map<String, Integer> audienceTag) {
        this.audienceTag = audienceTag;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public Long getId_author() {
        return id_author;
    }

    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    public Book parseToBook(UserRepository userRepository){
        User user = userRepository.findById((this.id_author))
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + this.id_author));

        return new Book(this.titleBook, this.language, this.imageBook, this.summaryBook, this.firstChapter,
                this.parseAudienceTagToString(), this.parseLinkToString(), user, null, null);
    }

    private String parseAudienceTagToString(){
        return this.parseMapToString(this.audienceTag);
    }

    private String parseLinkToString(){
        return this.parseMapToString(this.links);
    }

    private String parseMapToString(Map map){
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public BookInformation updateBookWithBookInformation(BookRepository bookRepository, UserRepository userRepository){
        User user;

        Book book = bookRepository.findById(this.idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + this.idBook));

        if(this.id_author != null) {
            user = userRepository.findById(this.id_author)
                    .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + this.id_author));
            book.setAuthor(user);
        }

        if(this.titleBook!= null && !(this.titleBook.equals("")))
            book.setBookTitle(this.titleBook);

        if(this.imageBook!= null && !(this.imageBook.equals("")))
            book.setBookImage(this.imageBook);

        if(this.language!= null && !(this.language.equals("")))
            book.setLanguage(this.language);

        if(this.summaryBook!= null && !(this.summaryBook.equals("")))
            book.setSummaryBook(this.summaryBook);

        if(this.firstChapter!= null && !(this.firstChapter.equals("")))
            book.setFirstChapter(this.firstChapter);

        if(this.audienceTag!= null && !(this.audienceTag.size() == 0))
            book.setAudienceTag(this.audienceTag);

        if(this.links!= null && !(this.links.size() == 0))
            book.setLinks(this.links);

        Book updateBook = bookRepository.save(book);

        return updateBook.parseToBookInformation();
    }
}
