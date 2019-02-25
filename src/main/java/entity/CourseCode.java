package entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

// TODO: Bad namedQuery, needs to be fixed.

// TODO: Should query coursecodes that have books with given isbn
//@NamedQuery(name = "CourseCode.findByIsbn", query = "SELECT cc FROM CourseCode cc where cc.books = :books")
@Data
@Entity
@NamedQuery(name = "CourseCode.findById", query = "SELECT cc FROM CourseCode cc where cc.courseCode = :courseCode")
public class CourseCode {

    @Id
    private String courseCode;
    @ManyToMany
    private List<Book> books;

}
