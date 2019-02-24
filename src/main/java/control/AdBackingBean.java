package control;

import core.AdBuilder;
import core.BookBuilder;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import service.AdFacade;
import service.CourseCodeFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Named("ad")
@ViewScoped
public class AdBackingBean implements Serializable {

    @EJB
    private AdFacade adFacade;
    @EJB
    private CourseCodeFacade courseCodeFacade;

    @Getter
    @Setter
    private String courseCode;
    @Getter
    @Setter
    private String bookName;
    @Getter
    @Setter
    private String authorName;
    @Getter
    @Setter
    private long isbn;
    @Getter
    @Setter
    private int price;

    public List<Ad> getAds() {
        return adFacade.findAll();
    }

    public void createAd() {

        Ad ad = new AdBuilder()
                .setPrice(price)
                .setCourseCodes(fetchCourseCodes())
                .setBook(fetchBook())
                .setUser(fetchUser())
                .build();

        adFacade.create(ad);

    }

    private List<CourseCode> fetchCourseCodes() {
        // TODO: Move this code to a CourseCodeBackingBean?
        List<CourseCode> courseCodes = courseCodeFacade.findByIsbn(isbn);
        // TODO: if course code not exists in the list. add it to list and database for future us
        // TODO: CourseCodes list will be empty in the earlier stages. Adding mock object at this point

        CourseCode cc = new CourseCode();
        cc.setBooks(new ArrayList<>());
        cc.setCourseCode(this.courseCode);

        courseCodes.add(cc);

        return courseCodes;
    }

    private Book fetchBook() {

        // TODO: check if this book already exists.
        // TODO: Move to bookbackingbean?

        Book book = new BookBuilder()
                .setAuthor(authorName)
                .setISBN(isbn)
                .setName(bookName)
                .build();
        return book;

    }

    private User fetchUser() {
        return null;
    }
}
