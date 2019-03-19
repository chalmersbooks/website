package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Book.findById", query = "SELECT book FROM Book book WHERE book.isbn = :isbn"),
        @NamedQuery(name = "Book.findByName", query = "SELECT book FROM Book book WHERE book.name = :name"),
        @NamedQuery(name = "Book.findBySearchTerm", query = "SELECT book.name FROM Book book WHERE " +
                "book.name LIKE CONCAT('%', :searchTerm, '%')")
})

public class Book {

    @Id
    private String isbn;
    private String authors;
    private String name;
    private int edition;
}