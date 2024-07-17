package com.alurachallenges.literalura.repository;

import com.alurachallenges.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(int birthYear, int deathYear);
}
