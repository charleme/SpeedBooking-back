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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    
    @GetMapping("/getFavTags/{idUser}")
    public List<String> getFavTags(@PathVariable Long idUser){
        User user = this.findUserById(idUser);
        List<String> fav = user.getPreferredGenres();
        return fav;
    }

    //find user by id
    public User findUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User does not exist at the id :" + id));
    }
    
    @GetMapping("/getTL/{idUser}")
    public List<BookInformation> getBooksbyGenre_algo1(@PathVariable Long idUser)
    {
    	List<Book> TL = new ArrayList<Book>();
    	
    	User user = this.findUserById(idUser);
    	
    	Pageable pageable_algo1 = PageRequest.of(0,12);
    	List<String> fav = user.getPreferredGenres();

    	List<Book> res_algo1 =  userRepository.algo1(idUser, fav, pageable_algo1);
    	
    	TL.addAll(res_algo1);
    	
    	List<Book> res_algo2 = new ArrayList<Book>();
    	List<Book> eligibles_algo2 = new ArrayList<Book>();
    	
    	if(res_algo1.isEmpty()) {
    		eligibles_algo2 =  userRepository.alt_eligible_algo2(idUser);
    	}
    	else {
    		eligibles_algo2 =  userRepository.eligible_algo2(idUser,TL);
    	}
    	for(Book book : eligibles_algo2) {
    		List<String> majTags = book.getMajorAudienceTags();
    		List<String> inter = new ArrayList<String>();
    		for(String tag : majTags) {
    			if(fav.contains(tag)) {
    				inter.add(tag);
    			}
    		}
    		if(!inter.isEmpty() && inter.size()<(26-res_algo1.size())) {
    			res_algo2.add(book);
    		}
    	}
    	
    	TL.addAll(res_algo2);
    	
    	Pageable pageable_random = PageRequest.of(0,30-TL.size());
    	
    	List<Book> res_random = userRepository.findRandomBooks_TL(idUser, TL, pageable_random);
    	
    	TL.addAll(res_random);
    	
    	List<BookInformation> User_TL =  TL
    			.stream()
                .map(book -> book.parseToBookInformation())
                .collect(Collectors.toList());
    	
    	Collections.shuffle(User_TL);
    	
    	return User_TL;
    }
    
    //Create the initial map of genre when a user is created
    public HashMap<String, Integer> buildMappedGenres(String[] list){
        List<String> listSelectedGenre= Arrays.stream(list).collect(Collectors.toList());
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
