package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/book/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return ResponseEntity.ok(bookRepository.save(book));
    }
    
    
    //update book informations
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updateBook){
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
        
        return ResponseEntity.ok(bookRepository.save(book));
    }

    @PutMapping("/likeBook/{id}")
    public ResponseEntity<Book> likeBook(@PathVariable Long idBook, @RequestBody Long idUser){
        return updateAudienceTag(idBook, idUser, true);
    }



    @PutMapping("/dislikeBook/{id}")
    public ResponseEntity<Book> dislikeBook(@PathVariable Long idBook, @RequestBody Long idUser){
        return updateAudienceTag(idBook, idUser, false);
    }

    private ResponseEntity<Book> updateAudienceTag(Long idBook, Long idUser, boolean like) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<Book> book = bookRepository.findById(idBook);

        if(!user.isPresent()){
            throw new IllegalArgumentException("id user doesn't exist");
        }else if(!book.isPresent()) {
            throw new IllegalArgumentException("id book doesn't exist");
        }

        book.get().applyChangeAlgorithm(like, user.get().getPreferredGenres());

        Book updatedBook = bookRepository.save(book.get());
        return ResponseEntity.ok(updatedBook);
    }
}
