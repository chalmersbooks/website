package view;

import entity.Book;
import entity.CourseCode;
import lombok.extern.java.Log;
import service.CourseCodeFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Named("courseCodes")
@ViewScoped
public class CourseCodeBackingBean implements Serializable {

    @EJB
    private CourseCodeFacade ccFacade;

    private CourseCode currentCode;

    public CourseCode getCourseCode(String cc, Book book) {
        currentCode = ccFacade.findById(cc);
        if (!isValid(currentCode)) {
            currentCode = createCourseCode(cc, book);
            ccFacade.create(currentCode);
        }

        return currentCode;
    }

    private CourseCode createCourseCode(String courseCode, Book book) {
        CourseCode cc= new CourseCode();
        List<Book> books = new ArrayList<>();
        books.add(book);
        cc.setBooks(books);
        cc.setCourseCode(courseCode);
        return cc;
    }

    private boolean isValid(CourseCode cc) {
        if (cc == null) {
            return false;
        }

        if (cc.getCourseCode().length() != 6) {
            return false;
        }

        if (cc.getBooks() == null) {
            return false;
        }

        return true;
    }

    public CourseCode getCourseCode(String name) {
        return ccFacade.findById(name);
    }

    public List<CourseCode> getCourseCodesFromBook(String isbn) {
        return ccFacade.findByBook(isbn);
    }

    public List<CourseCode> getAll() {
        return ccFacade.findAll();
    }

}
