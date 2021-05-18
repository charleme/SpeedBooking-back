package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id :" + id)).parseToBookInformation();
        return book;
    }
    
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBookById(@PathVariable Long id){
    	Map<String, Boolean> response = new HashMap<>();
    	response.put("deleted", Boolean.TRUE);
    	
    	bookRepository.deleteById(id);
    	
    	return ResponseEntity.ok(response);
    }

    //add book to the database
    @PostMapping("/addBook")
    public Book createBook(@RequestBody Book book){
        return bookRepository.save(book);
    }
    
    
    //update book informations
    @PutMapping("/updateBook/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updateBook){
        Book book = bookRepository.findById(id)
        		.orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id :" + id));
        book.setBookTitle(updateBook.getBookTitle());
        book.setLanguage(updateBook.getLanguage());
        book.setBookImage(updateBook.getBookImage());
        book.setSummary(updateBook.getSummary());
        book.setFirstChapter(updateBook.getFirstChapter());
        book.setAudienceTag(updateBook.getAudienceTag());
        book.setLinks(updateBook.getLinks());
        book.setAuthor(updateBook.getAuthor());
        book.setReaders(updateBook.getReaders());
        book.setBookGenres(updateBook.getBookGenres());
        
        return bookRepository.save(book);
    }
}
