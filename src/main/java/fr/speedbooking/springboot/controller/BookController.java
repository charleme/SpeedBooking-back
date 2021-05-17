package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/book/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<BookInformation> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(book -> book.parseToBookInformation())
                .collect(Collectors.toList());
    }

    //add book to the database
    @PostMapping("/addUser")
    public Book createUser(@RequestBody Book book){
        return bookRepository.save(book);
    }
}
