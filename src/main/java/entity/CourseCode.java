package entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class CourseCode {

    @Id
    private String courseCode;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> books;

}
