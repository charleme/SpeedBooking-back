package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.model.UserBook;
import fr.speedbooking.springboot.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/userbook/")
public class UserBookController {
    @Autowired
    UserBookRepository userBookRepository;

    @GetMapping("/findUserBook/{idUser}&{idBook}")
    public int getProgress(@PathVariable Long idUser, @PathVariable Long idBook){
        return userBookRepository.getProgress(idUser, idBook);
    }
}
