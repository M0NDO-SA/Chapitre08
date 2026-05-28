package bookstoread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BookShelfSpec {
    @Test
    public void shelfEmptyWhenNoBookAdded() throws Exception{
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        asserTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void bookshelfContainsTwoBooksAdded(){
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java");
        shelf.add("Code Complete");
        List<String> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks(){
        Bookshelf shelf = new Bookshelf();
        shelf.add();
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient(){
        BookShelf shelf = new BookShelf();
        shelf.add("Effective java", "Code complet");
        List<String> books = shelf.books();

        try{
            books.add("the Mythical Man-Month");
            fail(() -> "Should not be able to add book to books");

        } catch (Exception e){
            assertTrue(e instanceof UnsupportedOperationExeception, ()-> "Should throw UnsupportedOperationException.");
        }
    }
}
