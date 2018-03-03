package com.graphql.repository;

import com.graphql.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings({"ALL", "AlibabaClassMustHaveAuthor"})
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
