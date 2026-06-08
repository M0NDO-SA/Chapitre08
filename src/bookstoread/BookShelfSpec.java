package bookstoread;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplet;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() throws Exception {
        shelf = new BookShelf();
        effectiveJava = new Book("EffectiveJava", "joshau jones", LocalDate.of(2008, Month.MAY, 8));
        codeComplet = new Book("Code complet", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Fredderick Phillips", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
    }

    @Test
    public void shelfEmptyWhenNoBookAdded() throws Exception {
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void bookshelfContainsTwoBooksAdded() {
        shelf.add(effectiveJava);
        shelf.add(codeComplet);
        List<Book> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add(); // Appel vide de varargs sécurisé
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient() {
        shelf.add(effectiveJava, codeComplet);
        List<Book> books = shelf.books();

        try {
            books.add(mythicalManMonth);
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }
    }

    @Test
    void bookshelfArrangedByBookTitle() {
        shelf.add(effectiveJava, codeComplet, mythicalManMonth);
        List<Book> books = shelf.arrange();
        assertEquals(Arrays.asList(codeComplet, effectiveJava, mythicalManMonth), books, () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    void boosInBookShelfAreInInsertionOrderAfterCllingArrange() {
        shelf.add(codeComplet, effectiveJava, mythicalManMonth);
        shelf.arrange(); // On appelle arrange
        List<Book> books = shelf.books(); 
        assertEquals(Arrays.asList(codeComplet, effectiveJava, mythicalManMonth), books, () -> "Books in a bookshelf are in insertion order");
    }

    @Test
    void bookshelfArrangedByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplet, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
        assertEquals(asList(mythicalManMonth, effectiveJava, codeComplet), books, () -> "Books in a bookshelf are arranged in descending order of book title");
    }

    @Test
    @DisplayName("books inside bookshelf are grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear() {
        shelf.add(effectiveJava, codeComplet, mythicalManMonth, cleanCode);
        Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

        assertThat(booksByPublicationYear).containsKey(Year.of(2008));
        assertThat(booksByPublicationYear.get(Year.of(2008))).containsExactlyInAnyOrder(effectiveJava, cleanCode);

        assertThat(booksByPublicationYear).containsKey(Year.of(2004));
        assertThat(booksByPublicationYear.get(Year.of(2004))).containsExactly(codeComplet);

        assertThat(booksByPublicationYear).containsKey(Year.of(1975));
        assertThat(booksByPublicationYear.get(Year.of(1975))).containsExactly(mythicalManMonth);
    }

    @Test
    void groupBooksByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplet, mythicalManMonth);
        Map<String, List<Book>> grouped = shelf.groupBy(Book::getAuthor);
        assertThat(grouped).containsKey("Steve McConnel");
    }
}

