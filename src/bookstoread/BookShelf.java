package bookstoread;
import java.util.*;
import java.util.stream.Collectors;

public class BookShelf {
    private final List<String> books = new ArrayList();

    public void add(String bookToAdd){
        books.add(bookToAdd);
    }

    public List<String> books(){
        return Collections.unmodifiableList(books);
    }

    public void add (String...booksToAdd){
        books.addAll(Arrays.asList(booksToAdd));
    }

    public List<String> arrange(){
        return books.stream().sorted().collect(Collectors.toList());
    }
