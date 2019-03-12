package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

// TODO: Bad namedQuery, needs to be fixed.

// TODO: Should query coursecodes that have books with given isbn
//@NamedQuery(name = "CourseCode.findByIsbn", query = "SELECT cc FROM CourseCode cc where cc.books = :books")
@Data
@Entity
@EqualsAndHashCode(of = "courseCode")
@NamedQueries({
        @NamedQuery(name = "CourseCode.findById", query = "SELECT cc FROM CourseCode cc WHERE " +
                "cc.courseCode = :courseCode"),
        @NamedQuery(name = "CourseCode.findByBook", query = "SELECT cc FROM CourseCode cc WHERE " +
                "cc.books IN (SELECT book FROM Book book WHERE book.isbn = :isbn)"),
        @NamedQuery(name = "CourseCode.findByIds", query = "SELECT cc FROM CourseCode cc WHERE " +
                "cc.courseCode IN :listOfCodes")
})
public class CourseCode {

    @Id
    private String courseCode;
    @ManyToMany
    private List<Book> books;

}
