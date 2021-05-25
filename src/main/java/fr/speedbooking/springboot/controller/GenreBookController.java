package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.*;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.GenreBookRepository;
import fr.speedbooking.springboot.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/genrebook/")
public class GenreBookController {
    @Autowired
    GenreBookRepository genreBookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/getGenreBookScore/{idGenre}&{idBook}")
    public int getScore(@PathVariable Long idGenre, @PathVariable Long idBook){
        return genreBookRepository.getScore(idGenre, idBook);
    }

    @PutMapping("/updateScore/{idGenre}&{idBook}&{score}")
    public ResponseEntity<Integer> updateScore(@PathVariable Long idGenre, @PathVariable Long idBook, @PathVariable int score){
        GenreBook genreBook = genreBookRepository.getGenreBookByGenreIdAndBookId(idGenre, idBook);

        genreBook.setScore(score);
        genreBookRepository.save(genreBook);

        return ResponseEntity.ok(genreBook.getScore());
    }

    @PostMapping("/createGenreBook/{idGenre}&{idBook}&{score}")
    public ResponseEntity<Map<String, Boolean>> createGenreBook(@PathVariable Long idGenre, @PathVariable Long idBook, @PathVariable int score){
        Genre genre = genreRepository.findById(idGenre)
                .orElseThrow(() -> new RessourceNotFoundException("Genre does not exist at the id : " + idGenre));
        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + idBook));

        GenreBook genreBook = new GenreBook(book,genre, score);

        genreBookRepository.save(genreBook);

        Map<String, Boolean> response = new HashMap<>();
        response.put("created", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
