package view;

import controll.BookController;
import controll.CourseCodeController;
import controll.UserComponent;
import core.AdBuilder;
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
    private UserComponent userComponent;

    @Inject
    private CourseCodeBackingBean ccBean;
    @Inject
    private BookBackingBean bookBean;

    @EJB
    private AdFacade adFacade;

    @Getter
    @Setter
    private String nothing = "nothing";
    private boolean skip;

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
        allCourseCodes = ccBean.getAll();
        booksBelongingToCourseCode = new HashMap<>();

        ad = new MakingAd();
        ad.setBook(new Book());

    }

    public String save() {

        // TODO: guess we need som error check here if add is incomplete or something...

        adFacade.create(makeAd());
        Messages.addGlobal(Messages.createInfo("Add created"));

        return "list_add.xhtml?faces-redirect=true";

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
        return userComponent.getUser();
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
        String courseCodes = "";
        for (CourseCode cc : ad.getCourseCodes()) {
            courseCodes = courseCodes.concat(cc.getCourseCode()
                    .concat(", "));
        }
        ad.setShowableCourseCodes(courseCodes);
    }


    /**
     * OLD CODE FROM EXAMPLE. SAVED IF NEEDED OR AS INSPIRATION
     */

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String OLDonFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }


}
