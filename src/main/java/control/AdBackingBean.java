package control;

import core.AdBuilder;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import service.AdFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Log
@Named("ad")
@ViewScoped
public class AdBackingBean {

    @EJB
    private AdFacade facade;

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

    public void createAd() {

        Ad ad = new AdBuilder()
                .setPrice(price)
                .setCourseCode(fetchCourseCode())
                .setBook(fetchBook())
                .setUser(fetchUser())
                .build();

        facade.create(ad);

    }

    private CourseCode fetchCourseCode() {
        return null;
    }

    private Book fetchBook() {
        return null;
    }

    private User fetchUser() {
        return null;
    }
}
