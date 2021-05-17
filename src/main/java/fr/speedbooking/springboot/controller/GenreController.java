package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.front.GenreInformation;
import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/genre/")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;
    
    @GetMapping("/allGenres")
    public List<GenreInformation> getAllGenres(){
        return genreRepository.findAll()
                .stream()
                .map(genre -> genre.parseToGenreInformation())
                .collect(Collectors.toList());
    }

    @GetMapping("/findGenre/{id}")
    public ResponseEntity<GenreInformation> getGenreById(@PathVariable Long id){
        GenreInformation genre = genreRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Genre does not exist at the id :" + id)).parseToGenreInformation();
        return ResponseEntity.ok(genre);
    }

}