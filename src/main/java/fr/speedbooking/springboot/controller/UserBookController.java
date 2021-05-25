package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.UserBookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/userbook/")
public class UserBookController {
    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/getUserBookProgress/{idUser}&{idBook}")
    public int getProgress(@PathVariable Long idUser, @PathVariable Long idBook){
        return userBookRepository.getProgress(idUser, idBook);
    }

    @PutMapping("/updateProgress/{idUser}&{idBook}&{progress}")
    public ResponseEntity<Map<String, Integer>> updateProgress(@PathVariable Long idUser, @PathVariable Long idBook, @PathVariable int progress){
        UserBook userBook = userBookRepository.getUserBookByUserIdAndBookId(idUser, idBook);

        userBook.setProgress(progress);
        userBookRepository.save(userBook);

        Map<String, Integer> response = new HashMap<>();
        response.put("progress", userBook.getProgress());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/createUserBook/{idUser}&{idBook}")
    public ResponseEntity<Map<String, Boolean>> createUserBook(@PathVariable Long idUser, @PathVariable Long idBook){
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id : " + idUser));
        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new RessourceNotFoundException("Book does not exist at the id : " + idBook));

        UserBook userBook = new UserBook(user, book, 0);

        userBookRepository.save(userBook);

        Map<String, Boolean> response = new HashMap<>();
        response.put("created", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }
}
