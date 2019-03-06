package control;

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
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import service.AdFacade;
import service.BookFacade;
import service.CourseCodeFacade;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Named("adWizardBean")
@ViewScoped
public class AdWizardBackingBean implements Serializable {

    // TODO: Clean this class, ALOT!

    @Inject
    private SecurityContext securityContext;

    @Inject
    private CourseCodeBackingBean ccBean;
    @Inject
    private BookBackingBean bookBean;

    @EJB
    private CourseCodeFacade ccFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private AdFacade adFacade;
    @EJB
    private BookFacade bookFacade;

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
    private String markedBook;

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
        Book book = bookFacade.findById(ad.getBook().getIsbn());
        if (book != null) {
            if (book.equals(ad.getBook())) {
                return book;
            } else {
                // TODO: This will give a server error. Getting here should probably not happen.
                return null;
            }
        } else {
            book = ad.getBook();
            bookFacade.create(book);
            return book;
        }
    }

    private List<CourseCode> convertCourseCodeTags() {
        List<CourseCode> courseCodes = new ArrayList<>();
        String[] codes = ad.getShowableCourseCodes().replace(" ", "").split(",");
        log.info("Showable courses are: " + ad.getShowableCourseCodes());
        for (String code : codes) {
            log.info("Current: " + code);
            CourseCode cc = ccBean.getCourseCode(code);
            if (cc == null) {
                cc = makeCourseCode(code);
            }
            if (!cc.getBooks().contains(ad.getBook())) {
                cc.getBooks().add(ad.getBook());
                // TODO: Implement this method
                // ccFacade.update(cc);
            }
        }
        return courseCodes;
    }

    private CourseCode makeCourseCode(String codeName) {
        CourseCode cc = new CourseCode();
        cc.setCourseCode(codeName);
        List<Book> books = new ArrayList<>();
        books.add(ad.getBook());
        cc.setBooks(books);

        ccFacade.create(cc);
        return cc;
    }

    private User fetchUser() {
        return userFacade.getUserById(securityContext.getCallerPrincipal().getName());
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
        // TODO: Should we make new query instead of looping list?
        for (CourseCode cc : allCourseCodes) {
            if (cc.getCourseCode().equals(ad.getCourseCode())) {
                return cc;
            }
        }
        return null;
    }

    private String makeShowableName(Book book) {
        return book.getName()
                .concat(", Volume: ")
                .concat(String.valueOf(book.getEdition()))
                .concat(", ")
                .concat(book.getAuthors());
    }

    public void fillFormWithMarkedBook() {
        if (markedBook == null) {
            log.info("Marked book is null for some reason...");
            return;
        }
        Book markedBook = fetchMarkedBook();
        ad.setBook(markedBook);
    }

    private Book fetchMarkedBook() {
        return bookBean.getbyId(markedBook);
    }

    private void setInformationFields() {
        List<CourseCode> courseCodesBelongingToCurrentBook = getCourseCodesFromCurrentBook();
        ad.setCourseCodes(courseCodesBelongingToCurrentBook);
        setShowableCourseCodes();
        log.info("showable coursecodes are -> " + ad.getShowableCourseCodes());
    }

    private List<CourseCode> getCourseCodesFromCurrentBook() {
        List<CourseCode> courseCodes = new ArrayList<>();
        for (CourseCode cc : allCourseCodes) {
            if (cc.getBooks().contains(ad.getBook())) {
                courseCodes.add(cc);
            }
        }
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
