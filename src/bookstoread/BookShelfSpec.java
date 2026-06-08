package bookstoread;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;

import static java.util.Arrays.asList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplet;
    private  Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() throws Exception{
        shelf = new BookShelf();
        effectiveJava = new Book("EffectiveJava", "joshau jones", LocalDate.of(2008, Month.MAY, 8));
        codeComplet = new Book("Code complet", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Fredderick Phillips", LocalDate.of(1975, Month.JANUARY, 1));
    }

    @Test
    public void shelfEmptyWhenNoBookAdded() throws Exception{
        BookShelf shelf = new BookShelf();
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void bookshelfContainsTwoBooksAdded(){
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java");
        shelf.add("Code Complete");
        List<Book> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks(){
        BookShelf shelf = new BookShelf();
        shelf.add();
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient(){
        BookShelf shelf = new BookShelf();
        shelf.add("Effective java", "Code complet");
        List<Book> books = shelf.books();

        try{
            books.add(mythicalManMonth);
            fail(() -> "Should not be able to add book to books");

        } catch (Exception e){
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }
    }

    @Test
    void bookshelfArrangedByBookTitle(){
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java", "Code Complete","The Mythical Man-Month");
        List<Book> books = shelf.arrange();
        assertEquals(Arrays.asList("Code Complete", "Effective Java", "The Mythical Man-Month"), books, () -> "Books in a bookshelf should be arran,ged lexicography by book title");
    }

    @Test
    void boosInBookShelfAreInInsertionOrderAfterCllingArrange(){
        BookShelf shelf = new BookShelf();
        shelf.add(effectiveJava, codeComplet, mythicalManMonth);
        shelf.arrange();
        List<Book> books = shelf.arrange();
        assertEquals(Arrays.asList("Code Complete", "Effective Java", "The Mythical Man-Month"), books, () -> "Books in a bookshelf are in insertion order");
    }

    @Test
    void bookshelfArrangedByUserProvidedCriteria(){
        BookShelf shelf = new BookShelf();
        shelf.add(effectiveJava, codeComplet, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
        assertEquals(asList(mythicalManMonth, effectiveJava, codeComplet), books,() -> "Books in a bookshelf are arranged in descending order of book title");
    }

    @Test
    @DisplayName("books inside bookshelf are grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear() {
        shelf.add(effectiveJava, codeComplet, mythicalManMonth, cleanCode);
        Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();
        assertThat(booksByPublicationYear).containsKey(Year.of(2008)).containsValues(Arrays.asList(effectiveJava, cleanCode));
        assertThat(booksByPublicationYear).containsKey(Year.of(2004)).containsValues(Collections.singletonList(codeComplet));
        assertThat(booksByPublicationYear).containsKey(Year.of(1975)).containsValues(Collections.singletonList(mythicalManMonth));
    }
}
