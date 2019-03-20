package model.component;

import entity.Book;
import entity.CourseCode;
import service.CourseCodeFacade;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class CourseCodeComponent implements Serializable {

    @EJB
    private CourseCodeFacade ccFacade;

    public List<CourseCode> getCourseCodesFromStrings(List<String> codes) {
        return ccFacade.findByIds(codes);
    }

    public void createOrUpdate(String code, Book book) {
        List<String> codes = new ArrayList<>();
        codes.add(code);
        createOrUpdate(codes, book);
    }

    public void createOrUpdate(List<String> codes, Book book) {
        List<CourseCode> existingCourseCodes = getCourseCodesFromStrings(codes);
        List<CourseCode> newCourseCodes = createNonExistingCodes(existingCourseCodes, codes, book);

        createOrUpdate(existingCourseCodes);
        createOrUpdate(newCourseCodes);
    }

    public void createOrUpdate(List<CourseCode> courseCodes) {
        for (CourseCode cc : courseCodes) {
            ccFacade.createOrUpdate(cc);
        }
    }

    private List<CourseCode> createNonExistingCodes(List<CourseCode> existingCodes, List<String> givenCodes, Book book) {
        List<CourseCode> newCourseCodes = new ArrayList<>();
        for (String code : givenCodes) {
            CourseCode cc = makeCourseCode(code, book);
            if (!existingCodes.contains(cc)) {
                newCourseCodes.add(cc);
            }
        }
        return newCourseCodes;
    }

    private CourseCode makeCourseCode(String code, Book book) {
        CourseCode cc = new CourseCode();
        cc.setCourseCode(code.toUpperCase());

        List<Book> books = new ArrayList<>();
        books.add(book);
        cc.setBooks(books);

        return cc;
    }

    public CourseCode get(String courseCode) {
        return ccFacade.findById(courseCode.toUpperCase());
    }

    public List<CourseCode> getCourseCodesFromBook(Book book) {
        return ccFacade.findByBookId(book.getIsbn());
    }

    public List<CourseCode> getAllCourseCodes() {
        return ccFacade.findAll();
    }
}
