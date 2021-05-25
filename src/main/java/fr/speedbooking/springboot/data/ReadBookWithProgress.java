package fr.speedbooking.springboot.data;

import fr.speedbooking.springboot.front.ReadBookInformationWithProgress;
import fr.speedbooking.springboot.model.Book;

public class ReadBookWithProgress {
    int progress;
    Book book;

    public ReadBookWithProgress() {
    }

    public ReadBookWithProgress(int progress, Book book) {
        this.progress = progress;
        this.book = book;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ReadBookInformationWithProgress parseToReadBookInformation(){
        return new ReadBookInformationWithProgress(this.progress, this.book.parseToBookInformation());
    }
}
