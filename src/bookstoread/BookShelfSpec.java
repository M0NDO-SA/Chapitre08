package bookstoread;

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
}
