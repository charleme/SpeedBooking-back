package fr.speedbooking.springboot.controller;


import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.Genre;
import fr.speedbooking.springboot.model.User;
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
