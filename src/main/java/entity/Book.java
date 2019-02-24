package entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    private long isbn;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Author> authors;
    private String name;
    private int edition;

    public void addAuthor(Author author) {
        authors.add(author);
    }

}
