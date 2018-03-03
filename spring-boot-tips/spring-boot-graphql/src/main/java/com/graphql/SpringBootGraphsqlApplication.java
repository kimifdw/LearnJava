package com.graphql;

import com.graphql.domain.Author;
import com.graphql.domain.Book;
import com.graphql.repository.AuthorRepository;
import com.graphql.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author fudongwei
 */
@SpringBootApplication
public class SpringBootGraphsqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGraphsqlApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
        return (args) -> {
            Author author = new Author("Herbert", "Schildt");
            authorRepository.save(author);
            bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
        };
    }

}
