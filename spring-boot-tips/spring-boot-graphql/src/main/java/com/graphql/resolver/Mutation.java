package com.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.domain.Author;
import com.graphql.domain.Book;
import com.graphql.error.BookNotFoundException;
import com.graphql.repository.AuthorRepository;
import com.graphql.repository.BookRepository;

public class Mutation implements GraphQLMutationResolver {

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);
        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book(title, isbn, pageCount != null ? pageCount : 0, new Author(authorId));
        bookRepository.save(book);
        return book;
    }

    public Book newBook(String title, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setPageCount(pageCount != null ? pageCount : 0);
        book.setAuthor(new Author(authorId));
        bookRepository.save(book);
        return book;
    }


    public boolean deleteBook(Long id) {
        bookRepository.delete(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long authorId) {
        Book book = bookRepository.findOne(authorId);
        if (null == book) {
            throw new BookNotFoundException("the book to be updated was not found", authorId);
        }
        book.setPageCount(pageCount);
        bookRepository.save(book);
        return book;
    }
}
