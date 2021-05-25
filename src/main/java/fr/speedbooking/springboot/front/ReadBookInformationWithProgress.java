package fr.speedbooking.springboot.front;

import fr.speedbooking.springboot.data.ReadBookWithProgress;
import fr.speedbooking.springboot.repository.UserRepository;

public class ReadBookInformationWithProgress {
    int progress;
    BookInformation book;

    public ReadBookInformationWithProgress() {
    }

    public ReadBookInformationWithProgress(int progress, BookInformation book) {
        this.progress = progress;
        this.book = book;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public BookInformation getBook() {
        return book;
    }

    public void setBook(BookInformation book) {
        this.book = book;
    }

    public ReadBookWithProgress parseToReadBookWithProgress(UserRepository userRepository){
        return new ReadBookWithProgress(this.progress, this.book.parseToBook(userRepository));
    }
}
