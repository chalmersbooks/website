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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Named("adBean")
@ViewScoped
public class AdBackingBean implements Serializable {

    @EJB
    private AdFacade adFacade;
    @Inject
    private CourseCodeBackingBean ccBean;
    @Inject
    private BookBackingBean bookBean;

    @Getter
    @Setter
    private String courseCode;
    @Getter
    @Setter
    private int price;

    public List<Ad> getAds() {
        return adFacade.findAll();
    }

    public void createAd() {

        Ad ad = new AdBuilder()
                .setPrice(price)
                .setBook(fetchBook())
                .setCourseCodes(fetchCourseCodes())
                .setUser(fetchUser())
                .build();

        adFacade.create(ad);

    }

    private List<CourseCode> fetchCourseCodes() {
        // TODO: Also collect all the coursecodes that is set for the book.
        // CourseCode cc = ccBean.getCourseCode(courseCode);

        CourseCode cc = ccBean.getCourseCode(
                courseCode,
                bookBean.getCurrentBook()
        );

        List<CourseCode> courseCodes = new ArrayList<>();
        courseCodes.add(cc);

        return courseCodes;
    }

    private Book fetchBook() {
        return bookBean.getCurrentBook();
    }

    private User fetchUser() {

        // TODO: Temporary for testing at the moment
        // return real user later on

        User u = new User();
        u.setEmail("test@test.com");
        u.setPassword("123");
        return u;
    }

    public void getCourseCodesFromBook() {

        String customISBN = "1234";
        List<CourseCode> courseCodes = ccBean.getCourseCodesFromBook(customISBN);

        for (CourseCode c : courseCodes) {

            log.info(c.getCourseCode());

        }

    }
}
