package com.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.domain.Author;
import com.graphql.domain.Book;
import com.graphql.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {

    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findOne(book.getAuthor().getId());
    }
}
