package core;

import entity.Author;
import entity.Book;

import java.util.ArrayList;

public class BookBuilder {

    private Book book;

    public BookBuilder() {
        book = new Book();
        book.setAuthors(new ArrayList<>());
    }

    public BookBuilder setName(String name) {
        book.setName(name);
        return this;
    }

    public BookBuilder setISBN(long isbn) {
        book.setIsbn(isbn);
        return this;
    }

    public BookBuilder setAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        book.addAuthor(author);
        return this;
    }

    public Book build() {
        return book;
    }


}
