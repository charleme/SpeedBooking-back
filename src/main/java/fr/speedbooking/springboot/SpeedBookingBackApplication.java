package fr.speedbooking.springboot;

import fr.speedbooking.springboot.controller.BookController;
import fr.speedbooking.springboot.exception.RessourceNotFoundException;
import fr.speedbooking.springboot.model.Book;
import fr.speedbooking.springboot.model.GenreBook;
import fr.speedbooking.springboot.model.User;
import fr.speedbooking.springboot.model.UserBook;
import fr.speedbooking.springboot.repository.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpeedBookingBackApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpeedBookingBackApplication.class, args);
		BookController books = ctx.getBean(BookController.class);
		UserRepository users = ctx.getBean(UserRepository.class);
		
		User user = users.findById((long)7).get();
		Book book = new Book("Miss Peregrine et les enfants particuliers","Français", "https://oursebibliophile.files.wordpress.com/2015/05/miss-peregrine-1.jpg",
				"Jacob Portman, 16 ans, écoute depuis son enfance les récits fabuleux de son grand-père. Ce dernier, un juif polonais, a passé une partie de sa vie sur une minuscule île du pays de Galles, où ses parents l'avaient envoyé pour le protéger de la menace nazie. Le jeune Abe Portman y a été recueilli par Miss Peregrine Faucon, la directrice d'un orphelinat pour enfants « particuliers ». Selon ses dires, Abe y côtoyait une ribambelle d'enfants doués de capacités surnaturelles, censées les protéger des « Monstres ». Un soir, Jacob trouve son grand-père mortellement blessé par une créature qui s'enfuit sous ses yeux. Bouleversé, Jacob part en quête de vérité sur l'île si chère à son grand-père. En découvrant le pensionnat en ruines, il n'a plus aucun doute : les enfants particuliers ont réellement existé. Mais étaient-ils dangereux ? Pourquoi vivaient-ils ainsi reclus, cachés de tous ? Et s'ils étaient toujours en vie, aussi étrange que cela puisse paraître...",
				"— Millard ! Qui est le Premier ministre ?\n"
				+ "\n"
				+ "— Winston Churchill. Qu’est-ce qui te prend ? Tu as perdu la boule ?\n"
				+ "\n"
				+ "— Quelle est la capitale de la Birmanie ?\n"
				+ "\n"
				+ "— Ça alors, aucune idée ! Rangoon ?\n"
				+ "\n"
				+ "— Bravo ! Et ta date d’anniversaire ?\n"
				+ "\n"
				+ "— Tu veux bien arrêter de crier et me laisser saigner tranquille !",
				"{\"Angst\": 74, \"Crime\": 89, \"Drama\": 89, \"Humor\": 26, \"Quest\": 77, \"Family\": 25, \"Horror\": 76, \"Parody\": 19, \"Poetry\": 24, \"Sci-fi\": 65, \"Fantasy\": 28, \"Mystery\": 125, \"Romance\": 12, \"Tragedy\": 73, \"Western\": 35, \"Survival\": 56, \"Suspense\": 97, \"Adventure\": 99, \"Spiritual\": 77, \"Friendship\": 59, \"Hurt/Comfort\": 22, \"Supernatural\": 29, \"Homosexuality\": 7}",
				"{\"Fnac\":\"https://livre.fnac.com/a4082620/Miss-Peregrine-et-les-enfants-particuliers-Tome-1-Miss-peregrine-et-les-enfants-particuliers-Sidonie-Van-Den-Dries\"}",
				user, new HashSet<UserBook>(), new HashSet<GenreBook>());
		books.updateBook((long) 5, book);
	}
}
