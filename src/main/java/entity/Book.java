package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    private long isbn;
    @OneToMany
    private List<Author> authors;
    private String name;
    private int edition;

}
