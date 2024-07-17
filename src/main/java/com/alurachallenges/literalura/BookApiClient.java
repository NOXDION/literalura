package com.alurachallenges.literalura;

import com.alurachallenges.literalura.model.ApiResponse;
import com.alurachallenges.literalura.model.Author;
import com.alurachallenges.literalura.model.Book;
import com.alurachallenges.literalura.repository.AuthorRepository;
import com.alurachallenges.literalura.repository.BookRepository;
import com.alurachallenges.literalura.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class BookApiClient {

    private static final Logger LOGGER = Logger.getLogger(BookApiClient.class.getName());
    private final HttpClient httpClient;
    private final String baseUrl;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public BookApiClient(String baseUrl) {
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        this.baseUrl = baseUrl;
    }

    public Book fetchBookByTitle(String title) {
        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/books?search=" + encodedTitle))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ApiResponse apiResponse = JsonUtil.parseApiResponse(response.body());
                if (apiResponse != null && !apiResponse.getResults().isEmpty()) {
                    Book book = apiResponse.getResults().stream().findFirst().orElse(null);
                    if (book != null) {
                        Author author = book.getFirstAuthor();
                        if (author != null) {
                            author = authorRepository.save(author);
                            book.setAuthor(author);
                        }
                        book = bookRepository.save(book);
                        return book;
                    } else {
                        LOGGER.warning("No books found for the title: " + title);
                    }
                }
            } else {
                LOGGER.info("Failed to fetch books. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
        }
        return null;
    }

    public List<Book> getBookCatalog() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findAll().stream()
                .filter(book -> language.equalsIgnoreCase(book.getLanguage()))
                .collect(Collectors.toList());
    }

    public List<Author> getAuthorCatalog() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthorsAliveInYear(int year) {
        return authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(year, year);
    }

    public long countBooksByLanguage(String language) {
        return bookRepository.countByLanguage(language);
    }
}
