package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;

import java.util.Map;

public class ReadBookWithProgress {
    private Long idBook;
    private String titleBook;
    private String language;
    private String imageBook;
    private String summaryBook;
    private String firstChapter;
    private Map<String, Integer> audienceTag;
    private Map<String, String> links;
    private int progress;


    public ReadBookWithProgress() {
    }

    public ReadBookWithProgress(int progress, Book book) {
        this.progress = progress;
        BookInformation bookInformation = book.parseToBookInformation();
        this.idBook = bookInformation.getIdBook();
        this.titleBook = bookInformation.getTitleBook();
        this.language = bookInformation.getLanguage();
        this.imageBook = bookInformation.getImageBook();
        this.summaryBook = bookInformation.getSummaryBook();
        this.firstChapter = bookInformation.getFirstChapter();
        this.audienceTag = bookInformation.getAudienceTag();
        this.links = bookInformation.getLinks();
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
