package fr.speedbooking.springboot.model;

import fr.speedbooking.springboot.front.UserInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import java.util.*;

@Entity
public class User implements Serializable {
    public static final int NUMBER_PREFERRED_GENRES_ANALYSED = 3;
    @Id
    @GeneratedValue
    private Long idUser;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "genres")
    private String genres;

    @Column(name="languages")
    private String languages;

    @OneToMany(mappedBy="author", fetch=FetchType.EAGER)
    private Set<Book> books;

    @OneToMany(mappedBy = "idUser", fetch=FetchType.EAGER)
    private Set<UserBook> booksRead;

    public User() {
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public User(String username, String email, String password, String genres, String languages,
                Set<Book> books, Set<UserBook> booksRead) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.genres = genres;
        this.languages = languages;
        this.books = books;
        this.booksRead = booksRead;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Set<UserBook> getBooksRead() {
        return booksRead;
    }

    public void setBooksRead(Set<UserBook> booksRead) {
        this.booksRead = booksRead;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public UserInformation parseToUserInformation(){
        return new UserInformation(this.idUser, this.username, this.email, this.password, this.createTime, this.genres, this.languages);
    }

    public Map<String, Integer> getMappedGenres(){
        try {
            return new ObjectMapper().readValue(this.genres, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void setGenres(Map<String, Integer> mappedGenres){
        try {
            this.genres = new ObjectMapper().writeValueAsString(mappedGenres);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPreferredGenres(){
        List<String> preferredGenres = new ArrayList<>();

        Map<String, Integer> genres = this.getMappedGenres();

        Comparator<String> valueComparator = new Comparator<String>() {
            public int compare(String k1, String k2) {
                Integer v1 = genres.get(k1);
                Integer v2 = genres.get(k2);
                int compare = v2.compareTo(v1); //DESC
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }

            }
        };

        TreeMap<String,Integer> sortedByValues = new TreeMap<>(valueComparator);

        sortedByValues.putAll(genres);

        for (int i = 0; i < NUMBER_PREFERRED_GENRES_ANALYSED; i++) {
            preferredGenres.add(sortedByValues.pollFirstEntry().getKey());
        }

        return preferredGenres;
    }
}
