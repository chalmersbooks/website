package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    private String name;
    @ManyToMany
    private List<Book> books;

}
