package view;

import controll.*;
import lombok.Data;
import model.AdBuilder;
import model.MakingAd;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;
import lombok.extern.java.Log;
import model.bean.BookComponent;
import model.bean.CourseCodeComponent;
import org.omnifaces.util.Ajax;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Data
@Named("adWizardBean")
@ViewScoped
public class AdWizardBackingBean implements Serializable {

    @Inject
    private BookComponent bookComponent;
    @Inject
    private CourseCodeComponent ccComponent;
    @Inject
    private UserComponent userComponent;

    private List<CourseCode> allCourseCodes;
    private Map<String, String> booksBelongingToCourseCode;
    private MakingAd ad;
    private String markedBookId;

    @PostConstruct
    public void init() {
        allCourseCodes = ccComponent.getAllCourseCodes();
        booksBelongingToCourseCode = new HashMap<>();

        ad = new MakingAd();
        ad.setBook(new Book());

    }

    public Ad makeAd() {
        return new AdBuilder()
                .setBook(getCurrentBook())
                .setCourseCodes(convertCourseCodeTags())
                .setPrice(ad.getPrice())
                .setUser(fetchUser())
                .build();
    }

    private Book getCurrentBook() {
        bookComponent.createOrUpdate(ad.getBook());
        return ad.getBook();
    }

    private List<CourseCode> convertCourseCodeTags() {
        String[] codes = ad.getShowableCourseCodes().replace(" ", "").split(",");
        ccComponent.createOrUpdate(codes, ad.getBook());
        return ccComponent.getCourseCodesFromStrings(codes);
    }

    private User fetchUser() {
        return userComponent.getUser();
    }

    public void setFoundBooks() {
        log.info("Setting found books");
        CourseCode cc = getCurrentCourseCode();
        if (cc == null) {
            return;
        }
        log.info("Found books are:");
        for (Book b : cc.getBooks()) {
            log.info(b.getName());
            booksBelongingToCourseCode.put(makeShowableName(b), b.getIsbn());
        }
        Ajax.update("BooksButtons");
    }

    private CourseCode getCurrentCourseCode() {
        return ccComponent.get(ad.getCourseCode());
    }

    private String makeShowableName(Book book) {
        return book.getName()
                .concat(", Volume: ")
                .concat(String.valueOf(book.getEdition()))
                .concat(", ")
                .concat(book.getAuthors());
    }

    public void fillFormWithMarkedBook() {
        if (this.markedBookId == null) {
            return;
        }
        Book markedBook = bookComponent.get(this.markedBookId);
        ad.setBook(markedBook);
    }

    public void setInformationFields() {
        List<CourseCode> courseCodesBelongingToCurrentBook = getCourseCodesFromCurrentBook();
        ad.setCourseCodes(courseCodesBelongingToCurrentBook);
        setShowableCourseCodes();
        log.info("showable coursecodes are -> " + ad.getShowableCourseCodes());
    }

    private List<CourseCode> getCourseCodesFromCurrentBook() {

        List<CourseCode> courseCodes = ccComponent.getCourseCodesFromBook(ad.getBook());

        if (!containsGivenCourse(ad.getCourseCode(), courseCodes)) {
            CourseCode cc = new CourseCode();
            List<Book> books = new ArrayList<>();
            books.add(ad.getBook());
            cc.setBooks(books);
            cc.setCourseCode(ad.getCourseCode());
            courseCodes.add(cc);
        }

        return courseCodes;

    }

    private boolean containsGivenCourse(String courseCode, List<CourseCode> courseCodes) {
        for (CourseCode cc : courseCodes) {
            if (cc.getCourseCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    private void setShowableCourseCodes() {
        // TODO: This is not working as intended...
        String courseCodes = "";
        for (CourseCode cc : ad.getCourseCodes()) {
            courseCodes = courseCodes.concat(cc.getCourseCode()
                    .concat(", "));
        }
        ad.setShowableCourseCodes(courseCodes);
    }

}
