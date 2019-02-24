package entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

// TODO: Bad namedQuery, needs to be fixed.
// TODO: Should query coursecodes that have books with given isbn
@Data
@Entity
@NamedQuery(name = "CourseCode.findByIsbn", query = "SELECT cc FROM CourseCode cc where cc.books = :books")
public class CourseCode {

    @Id
    private String courseCode;
    @ManyToMany
    private List<Book> books;

}
