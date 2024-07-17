package com.alurachallenges.literalura.repository;

import com.alurachallenges.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT COUNT(b) FROM Book b WHERE b.language = :language")
    long countByLanguage(String language);
}
