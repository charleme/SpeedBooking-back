package fr.speedbooking.springboot.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.speedbooking.springboot.model.User;


import java.sql.Timestamp;
import java.util.Map;


public class UserInformation {
    private Long idUser;

    private String username;

    private String email;

    private String password;

    private Timestamp createTime;

    private Map<String, Integer> genres;

    private String languages;

    public UserInformation() {
    }

    public UserInformation(Long idUser, String username, String email, String password, Timestamp createTime, Map<String, Integer> genres, String languages) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.genres = genres;
        this.languages = languages;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public Map<String, Integer> getGenres() {
        return genres;
    }

    public void setGenres(Map<String, Integer> genres) {
        this.genres = genres;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    private String parseGenreToString(){
        try {
            return new ObjectMapper().writeValueAsString(this.genres);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    send user without books written and read
     */
    public User parseToUser(){
        return new User(this.username, this.email, this.password, this.parseGenreToString(), this.languages,
                null, null);
    }
}
