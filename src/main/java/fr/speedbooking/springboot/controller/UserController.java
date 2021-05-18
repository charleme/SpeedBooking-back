package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.Genre;
import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.GenreRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;


    // get all users
    @GetMapping("/allUser")
    public List<UserInformation> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> user.parseToUserInformation())
                .collect(Collectors.toList());
    }

    //get user by Id
    @GetMapping("/findUser/{id}")
    public ResponseEntity<UserInformation> getUserById(@PathVariable Long id){
        UserInformation user = findUserById(id).parseToUserInformation();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/addUser")
    public User createUserFinal(@RequestBody User user, @RequestBody String[] list){
        user.setGenres(buildMappedGenres(list));
        return userRepository.save(user);
    }

    //update user by id
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser){
        User user = findUserById(id);
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setGenres(newUser.getGenres());
        user.setLanguages(newUser.getLanguages());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/updateUser/{user_id}&{book_id}")
    public ResponseEntity<User> updateUserGenre(@PathVariable Long user_id, @PathVariable Long book_id){
        User user = findUserById(user_id);
        Book books = bookRepository.findById(book_id).orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + book_id));
        Map<String, Integer> newGenre = user.getMappedGenres();
        System.out.println(user.getGenres());
        for(GenreBook genreBook : books.getBookGenres()){
                    String genreName = genreBook.getIdGenre().getNameGenre();
                    newGenre.put(genreName, newGenre.get(genreName) + (genreBook.getScore()*25/100));
        }
        System.out.println(newGenre);
        user.setGenres(newGenre);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    //delete user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
        User user = findUserById(id);

        userRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    //find user by id
    public User findUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + id));
    }

    public HashMap<String, Integer> buildMappedGenres(String[] list){
        List<String> listSelectedGenre= Arrays.stream(list).toList();
        List<Genre> genres = genreRepository.findAll();
        HashMap<String, Integer> newGenreMap = new HashMap<String, Integer>();

        for(Genre elem: genres){
            if(listSelectedGenre.contains(elem.getNameGenre())){
                newGenreMap.putIfAbsent(elem.getNameGenre(), 40);
            }else{
                newGenreMap.putIfAbsent(elem.getNameGenre(), 0);
            }
        }
        return newGenreMap;
    }
}
