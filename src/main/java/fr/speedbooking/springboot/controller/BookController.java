package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.front.GenreWithScore;
import fr.speedbooking.springboot.controller.dataStructure.CreateBookData;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.front.GenreInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.Genre;
import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.GenreBookRepository;
import fr.speedbooking.springboot.repository.GenreRepository;
import fr.speedbooking.springboot.repository.UserBookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private GenreBookRepository genreBookRepository;
    
    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/books")
    public List<BookInformation> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(Book::parseToBookInformation)
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

        List<GenreBook> bookGenres = bookRepository.findGenreBooksByBookId(id);
        List<UserBook> userBooks = bookRepository.findUserBooksByBookId(id);

        if(bookGenres != null)
            for (GenreBook bookGenre : bookGenres) {
                genreBookRepository.delete(bookGenre);
            }

        if(userBooks != null)
            for (UserBook userBook: userBooks) {
                userBookRepository.delete(userBook);
            }
    	
    	bookRepository.deleteById(id);
    	
    	return ResponseEntity.ok(response);
    }

    //add book to the database
    @PostMapping("/addBook")
    public ResponseEntity<BookInformation> createBook(@RequestBody CreateBookData createBookData) {
    	
    	BookInformation book = createBookData.book;
    	List<Genre> genresList = genreRepository.findAll();
    	HashMap<String, Integer> initializedGenresMap = new HashMap<String, Integer>();
    	
    	for (Genre genre: genresList) {
    		initializedGenresMap.put(genre.getNameGenre(), 0);
    	}
    
    	book.setAudienceTag(initializedGenresMap);
        Book createdBook = bookRepository.save(book.parseToBook(userRepository));

    	for(GenreInformation genreInformation : createBookData.genres) {
    	    Genre genre = genreRepository.findById(genreInformation.getIdGenre())
                    .orElseThrow(() -> new RessourceNotFoundException("Genre does not exist at the id : " + genreInformation.getIdGenre()));
    	    GenreBook genrebook = new GenreBook(createdBook, genre, 40);
            genreBookRepository.save(genrebook);
    	}

        return ResponseEntity.ok(createdBook.parseToBookInformation());
    }
    
    
    //update book informations
    @PutMapping("/updateBook")
    public ResponseEntity<BookInformation> updateBook(@RequestBody CreateBookData updateBook){
        Book book = updateBook.book.updateBookWithBookInformation(bookRepository, userRepository);

    	GenreInformation[] updateGenres = updateBook.genres;
    	List<GenreBook> bookGenres = bookRepository.findGenreBooksByBookId(updateBook.book.getIdBook());
    	for(GenreBook genreBook : bookGenres) {
    		genreBookRepository.delete(genreBook);
    	}

    	for(GenreInformation genreInformation : updateGenres) {
            Genre genre = genreRepository.findById(genreInformation.getIdGenre())
                    .orElseThrow(() -> new RessourceNotFoundException("Genre does not exist at the id : " + genreInformation.getIdGenre()));
            GenreBook genrebook = new GenreBook(book, genre, 40);
            genreBookRepository.save(genrebook);
    	}
        return ResponseEntity.ok(book.parseToBookInformation());
    }

    @GetMapping("/bookGenresWithScore/{idBook}")
    public List<GenreWithScore> getBookGenresWithScore(@PathVariable Long idBook){
        return bookRepository.getGenreBooksWithScore(idBook);
    }

    @GetMapping("/isAuthor/{idUser}&{idBook}")
    public boolean isAuthor(@PathVariable Long idUser, @PathVariable Long idBook){
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + idUser));

        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + idBook));

        User author = book.getAuthor();
        return user.equals(author);
    }

    @PutMapping( "/likeBook/{idBook}&{idUser}")
    public ResponseEntity<Map<String, Integer>> likeBook(@PathVariable Long idBook, @PathVariable Long idUser){
        return updateAudienceTag(idBook, idUser, true);
    }

    @PutMapping("/dislikeBook/{idBook}&{idUser}")
    public ResponseEntity<Map<String, Integer>> dislikeBook(@PathVariable Long idBook, @PathVariable Long idUser){
        return updateAudienceTag(idBook, idUser, false);
    }
    
    @GetMapping("/getMajTags/{id}")
    public List<String> getFavTags(@PathVariable Long id){
        Book book = bookRepository.findById(id).get();
        List<String> tags = book.getMajorAudienceTags();
        return tags;
    }

    private ResponseEntity<Map<String, Integer>> updateAudienceTag(Long idBook, Long idUser, boolean like) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + idUser));

        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + idBook));

        book.applyChangeAlgorithm(like, user.getPreferredGenres());

        Book updatedBook = bookRepository.save(book);

        return ResponseEntity.ok(updatedBook.getMappedAudienceTag());
    }
}
