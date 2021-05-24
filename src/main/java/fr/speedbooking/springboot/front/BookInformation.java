package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BookInformation {
    private Long idBook;

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

    public BookInformation(Long idBook, String titleBook, String language, String imageBook, String summaryBook, String firstChapter, String audienceTag, String links, Long id_author) {
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

    public Book parseToBook(UserRepository userRepository){
        User user = userRepository.findById((this.id_author))
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + this.id_author));

        return new Book(this.titleBook, this.language, this.imageBook, this.summaryBook, this.firstChapter,
                this.audienceTag, this.links, user, null, null);
    }

    public BookInformation updateBookWithBookInformation(BookRepository bookRepository, UserRepository userRepository){
        Book book = bookRepository.findById(this.idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + this.idBook));

        User user = userRepository.findById(this.id_author)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + this.id_author));

        book.setBookTitle(this.titleBook);
        book.setBookImage(this.imageBook);
        book.setLanguage(this.language);
        book.setFirstChapter(this.firstChapter);
        book.setAudienceTag(this.audienceTag);
        book.setLinks(this.links);
        book.setAuthor(user);
        Book updateBook = bookRepository.save(book);

        return updateBook.parseToBookInformation();
    }
}
