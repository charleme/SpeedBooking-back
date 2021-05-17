package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    
    @GetMapping("/findBook/{id}")
    public BookInformation getBookById(@PathVariable Long id){
        BookInformation book = bookRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + id)).parseToBookInformation();
        return book;
    }
    
    @GetMapping("/deleteBook/{id}")
    public void deleteBookById(@PathVariable Long id){
    	bookRepository.deleteById(id);
    }

    //add book to the database
    @PostMapping("/addBook")
    public Book createBook(@RequestBody Book book){
        return bookRepository.save(book);
    }
}
