package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        for(Book book : books){
            System.out.println(book.getReaders());
        }

        return books;
    }

    //add user to the database
    @PostMapping("/addUser")
    public Book createUser(@RequestBody Book book){
        return bookRepository.save(book);
    }
}
