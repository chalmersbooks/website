package entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> books;

}
