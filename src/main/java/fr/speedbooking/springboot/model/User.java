package fr.speedbooking.springboot.model;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @OneToMany(mappedBy = "author")
	private Collection<Book> myBooks;

    public User() {

    }

    public User(String username, String email, String password, Timestamp createTime, String genres) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.genres = genres;
    }

    @Id
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long id_user) {
        this.idUser = id_user;
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

    public void setCreateTime(Timestamp create_time) {
        this.createTime = create_time;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
    
    public Collection<Book> getMyBooks() {
    	return myBooks;
    }
    
    public void setMyBooks(Collection<Book> myBooks) {
    	this.myBooks = myBooks;
    }
}
