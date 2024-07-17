package com.alurachallenges.literalura;

import com.alurachallenges.literalura.model.Author;
import com.alurachallenges.literalura.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BookApiClient apiClient;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("Menu:");
			System.out.println("1. Search for books by title");
			System.out.println("2. List all books");
			System.out.println("3. List books by language");
			System.out.println("4. List authors");
			System.out.println("5. List authors alive in a given year");
			System.out.println("6. Count books by language");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();  // Consume newline

			switch (choice) {
				case 1:
					System.out.print("Enter book title: ");
					String title = scanner.nextLine();
					Book book = apiClient.fetchBookByTitle(title);
					if (book != null) {
						System.out.println("Book found: " + book);
					} else {
						System.out.println("No book found with the title: " + title);
					}
					break;
				case 2:
					List<Book> catalog = apiClient.getBookCatalog();
					if (catalog.isEmpty()) {
						System.out.println("No books in the catalog.");
					} else {
						System.out.println("Book Catalog:");
						catalog.forEach(bookItem -> System.out.println(bookItem.toString()));
					}
					break;
				case 3:
					System.out.print("Enter language: ");
					String language = scanner.nextLine();
					List<Book> booksByLanguage = apiClient.getBooksByLanguage(language);
					if (booksByLanguage.isEmpty()) {
						System.out.println("No books found in the language: " + language);
					} else {
						System.out.println("Books in " + language + ":");
						booksByLanguage.forEach(bookItem -> System.out.println(bookItem.toString()));
					}
					break;
				case 4:
					List<Author> authors = apiClient.getAuthorCatalog();
					if (authors.isEmpty()) {
						System.out.println("No authors found.");
					} else {
						System.out.println("Authors:");
						authors.forEach(author -> System.out.println(author.toString()));
					}
					break;
				case 5:
					System.out.print("Enter year: ");
					int year = scanner.nextInt();
					scanner.nextLine();  // Consume newline
					List<Author> authorsAliveInYear = apiClient.getAuthorsAliveInYear(year);
					if (authorsAliveInYear.isEmpty()) {
						System.out.println("No authors found alive in the year: " + year);
					} else {
						System.out.println("Authors alive in " + year + ":");
						authorsAliveInYear.forEach(author -> System.out.println(author.toString()));
					}
					break;
				case 6:
					System.out.print("Enter language to count books: ");
					String countLanguage = scanner.nextLine();
					long count = apiClient.countBooksByLanguage(countLanguage);
					System.out.println("Number of books in " + countLanguage + ": " + count);
					break;
				case 7:
					running = false;
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}

		scanner.close();
	}
}
