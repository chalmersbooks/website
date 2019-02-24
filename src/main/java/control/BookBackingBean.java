package control;

import core.BookBuilder;
import entity.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import service.BookFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named("book")
@ViewScoped
public class BookBackingBean implements Serializable {

    @EJB
    private BookFacade bookFacade;
    @Getter
    @Setter
    private String bookName;
    @Getter
    @Setter
    private String authorNames;
    @Getter
    @Setter
    private long isbn;

    private Book currentBook;

    public Book getCurrentBook() {
        currentBook = bookFacade.findById(isbn);
        if (!isCurrentBookValid()) {
            log.info("Book \"" + bookName + "\" was not found. Creating...");
            currentBook = createBook();
            log.info("Book with name \"" + bookName + "\" created complete!");
            bookFacade.create(currentBook);
        }

        return currentBook;
    }

    // TODO: Later maybe even validate on
    // https://isbndb.com/apidocs
    public boolean isCurrentBookValid() {

        /* TODO
            check that isbn is valid and authors and name is set.
         */

        return false; // change this when implemented
    }

    private Book createBook() {
        Book book = new BookBuilder()
                .setName(bookName)
                .setISBN(isbn)
                .setAuthor(authorNames)
                .build();
        return book;
    }



}
