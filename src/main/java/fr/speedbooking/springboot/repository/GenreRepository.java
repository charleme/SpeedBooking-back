package fr.speedbooking.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.speedbooking.springboot.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
