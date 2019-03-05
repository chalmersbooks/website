package control;

import core.MakingAd;
import entity.Book;
import entity.CourseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.omnifaces.util.Ajax;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Named("adWizardBean")
@ViewScoped
public class AdWizardBackingBean implements Serializable {

    @Inject
    private CourseCodeBackingBean ccBean;
    @Inject
    private BookBackingBean bookBean;

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

    public void save() {
        /*FacesMessage msg = new FacesMessage("Successful", "Welcome");
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
    }

    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();

        log.info("Next step is -> " + nextStep);
        if (nextStep.equals("book")) {
            setFoundBooks();
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
