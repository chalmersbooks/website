package core;

import entity.Book;

public class BookBuilder {

    private Book book;

    public BookBuilder() {
        book = new Book();
    }

    public BookBuilder setName(String name) {
        book.setName(name);
        return this;
    }

    public BookBuilder setISBN(String isbn) {
        book.setIsbn(isbn);
        return this;
    }

    public BookBuilder setAuthor(String authors) {
        book.setAuthors(authors);
        return this;
    }

    public Book build() {
        return book;
    }


}
