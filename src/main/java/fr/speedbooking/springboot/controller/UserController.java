package fr.speedbooking.springboot.controller;

import fr.speedbooking.springboot.controller.dataStructure.CreateUserData;
import fr.speedbooking.springboot.front.BookInformation;
import fr.speedbooking.springboot.front.ReadBookWithProgress;
import fr.speedbooking.springboot.front.UserInformation;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.*;
import fr.speedbooking.springboot.repository.BookRepository;
import fr.speedbooking.springboot.repository.GenreRepository;
import fr.speedbooking.springboot.repository.UserBookRepository;
import fr.speedbooking.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
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

    @Autowired
    private UserBookRepository userBookRepository;


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
    public UserInformation createUserFinal(@RequestBody CreateUserData createUserData){
        User user = createUserData.user.parseToUser();
        user.setGenres(buildMappedGenres(createUserData.list));
        return userRepository.save(user).parseToUserInformation();
    }

    //update user by id
    @PutMapping("/updateUser")
    public ResponseEntity<UserInformation> updateUser(@RequestBody UserInformation updateUser){

        User user = findUserById(updateUser.getIdUser());
        if(user.getUsername() != null && !(user.getUsername().equals("")))
            user.setUsername(updateUser.getUsername());

        if(user.getEmail() != null && !(user.getEmail().equals("")))
            user.setEmail(updateUser.getEmail());

        if(user.getPassword() != null && !(user.getPassword().equals("")))
            user.setPassword(updateUser.getPassword());

        if(user.getGenres() != null && !(user.getGenres().equals("")))
            user.setGenres(updateUser.getGenres());

        if(user.getLanguages() != null && !(user.getLanguages().equals("")))
            user.setLanguages(updateUser.getLanguages());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser.parseToUserInformation());
    }

    @PutMapping("/resetUserGenres/{id}")
    public ResponseEntity<UserInformation> resetUserGenres(@PathVariable Long id, @RequestBody String[] list){
        User user = findUserById(id);
        user.setGenres(buildMappedGenres(list));
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser.parseToUserInformation());
    }

    @PutMapping("editPassword/{idUser}&{password}")
    public ResponseEntity<UserInformation> editPassword(@PathVariable Long idUser, @PathVariable String password){
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + idUser));

        user.setPassword(password);
        userRepository.save(user);

        return ResponseEntity.ok(user.parseToUserInformation());
    }

    @PutMapping("/updateUser/{user_id}/{book_id}")
    public ResponseEntity<User> updateUserGenre(@PathVariable Long user_id, @PathVariable Long book_id){
        User user = findUserById(user_id);
        Book book = bookRepository.findById(book_id).orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + book_id));
        Map<String, Integer> newGenre = user.getMappedGenres();
        for(GenreBook genreBook : book.getBookGenres()){
                    String genreName = genreBook.getIdGenre().getNameGenre();
                    newGenre.put(genreName, newGenre.get(genreName) + (genreBook.getScore()*25/100));
        }

        System.out.println(user.getBooksRead());
        user.setGenres(newGenre);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    //delete user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
        User user = findUserById(id);
        List<Book> writtenBooks = userRepository.findWrittenBooksByUserId(id);
        List<UserBook> userBooks = userRepository.findUserBooksByUserId(id);

        if(writtenBooks != null)
            for (Book book : writtenBooks) {
                bookRepository.delete(book);
            }

        if(userBooks != null)
            for (UserBook readBook: userBooks) {
                userBookRepository.delete(readBook);
            }

        userRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserReadBooks/{id}")
    public List<ReadBookWithProgress> getReadBook(@PathVariable Long id){
        return this.userRepository.getReadBooksWithProgress(id);
    }

    @GetMapping("/isUserPassword/{idUser}&{password}")
    public boolean isUserPassword(@PathVariable Long idUser, @PathVariable String password){
        User user = findUserById(idUser);

        return user.isPassword(password);
    }

    @GetMapping("/getWrittenBooks/{idUser}")
    public List<BookInformation> getWrittenBooks(@PathVariable Long idUser){
        return bookRepository.getWrittenBooks(idUser)
                .stream()
                .map(Book::parseToBookInformation)
                .collect(Collectors.toList());
    }

    //find user by id
    public User findUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + id));
    }

    //Create the initial map of genre when a user is created
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
