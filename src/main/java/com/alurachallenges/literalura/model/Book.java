package com.alurachallenges.literalura.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title")
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    @JsonAlias("languages")
    private String language;

    @JsonAlias("download_count")
    private int downloadCount;

    @JsonAlias("authors")
    @Transient
    private List<Author> authors;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    // Método para obtener el primer autor de la lista de autores
    public Author getFirstAuthor() {
        return authors != null && !authors.isEmpty() ? authors.stream().findFirst().orElse(null) : null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", language='" + language + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
