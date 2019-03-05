package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedQuery(name = "Book.findById", query = "SELECT book FROM Book book WHERE book.isbn = :isbn")
public class Book {

    @Id
    private String isbn;
    private String authors;
    private String name;
    private int edition;
}