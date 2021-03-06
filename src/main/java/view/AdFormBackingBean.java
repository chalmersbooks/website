package view;

import model.AdBuilder;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import model.component.BookComponent;
import model.component.CourseCodeComponent;
import model.component.UserComponent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Named("adFormBean")
@ViewScoped
public class AdFormBackingBean implements Serializable {

    @Inject
    private CourseCodeComponent ccComponent;
    @Inject
    private BookComponent bookComponent;
    @Inject
    private UserComponent userComponent;

    @Getter
    @Setter
    private Book book;
    @Getter
    @Setter
    private String courseCode;
    @Getter
    @Setter
    private int price;

    @PostConstruct
    public void init() {
        book = new Book();
    }

    public Ad makeAd() {

        return new AdBuilder()
                .setPrice(price)
                .setBook(fetchBook())
                .setCourseCodes(fetchCourseCodes())
                .setUser(fetchUser())
                .build();
    }

    private List<CourseCode> fetchCourseCodes() {
        ccComponent.createOrUpdate(courseCode, book);
        CourseCode cc = ccComponent.get(courseCode);


        List<CourseCode> courseCodes = new ArrayList<>();
        courseCodes.add(cc);

        return courseCodes;
    }

    private Book fetchBook() {
        bookComponent.createOrUpdate(book);
        return book;
    }

    private String fetchUser() {
        return userComponent.getUser().getEmail();
    }
}
