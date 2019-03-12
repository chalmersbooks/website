package view;

import controll.*;
import core.AdBuilder;
import core.LoggedInUser;
import core.MakingAd;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import service.AdFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Named("adWizardBean")
@ViewScoped
public class AdWizardBackingBean implements Serializable {

    @Inject
    private BookController bookController;
    @Inject
    private CourseCodeController ccController;
    @Inject
    private UserController userController;
    @Inject
    private AdController adController;

    @Getter
    @Setter
    private List<CourseCode> allCourseCodes;
    @Getter
    @Setter
    private Map<String, String> booksBelongingToCourseCode;

    @Getter
    @Setter
    private MakingAd ad;

    @Getter
    @Setter
    private String markedBookId;

    @PostConstruct
    public void init() {
        allCourseCodes = ccController.getAllCourseCodes();
        booksBelongingToCourseCode = new HashMap<>();

        ad = new MakingAd();
        ad.setBook(new Book());

    }

    public String save() {

        // TODO: guess we need som error check here if add is incomplete or something...
        try {
            adController.createAd(makeAd());
            Messages.addGlobal(Messages.createInfo("Add created"));
            return "list_add.xhtml?faces-redirect=true";
        } catch (AdException ae) {
            Messages.addGlobal(Messages.createError(ae.getMessage()));
        }
        return "";
    }

    private Ad makeAd() {
        return new AdBuilder()
                .setBook(getCurrentBook())
                .setCourseCodes(convertCourseCodeTags())
                .setPrice(ad.getPrice())
                .setUser(fetchUser())
                .build();
    }

    private Book getCurrentBook() {
        bookController.createOrUpdate(ad.getBook());
        return ad.getBook();
    }

    private List<CourseCode> convertCourseCodeTags() {
        String[] codes = ad.getShowableCourseCodes().replace(" ", "").split(",");
        ccController.createOrUpdate(codes, ad.getBook());
        return ccController.getCourseCodesFromStrings(codes);
    }

    private User fetchUser() {
        LoggedInUser user = userController.getUser();
        return userController.convertToEntity(user);
    }

    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();

        log.info("Next step is -> " + nextStep);
        if (nextStep.equals("book")) {
            setFoundBooks();
        } else if (nextStep.equals("information")) {
            setInformationFields();
        }

        return nextStep;
    }

    private void setFoundBooks() {
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
        return ccController.get(ad.getCourseCode());
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
        Book markedBook = bookController.get(this.markedBookId);
        ad.setBook(markedBook);
    }

    private void setInformationFields() {
        List<CourseCode> courseCodesBelongingToCurrentBook = getCourseCodesFromCurrentBook();
        ad.setCourseCodes(courseCodesBelongingToCurrentBook);
        setShowableCourseCodes();
        log.info("showable coursecodes are -> " + ad.getShowableCourseCodes());
    }

    private List<CourseCode> getCourseCodesFromCurrentBook() {

        List<CourseCode> courseCodes = ccController.getCourseCodesFromBook(ad.getBook());

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
