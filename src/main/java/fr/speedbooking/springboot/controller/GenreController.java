package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/genre/")
public class GenreController {

    @Autowired
    public GenreRepository genreRepository;




}