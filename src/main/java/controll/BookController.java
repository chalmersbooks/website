package controll;

import entity.Book;
import service.BookFacade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class BookController implements Serializable {

    @EJB
    private BookFacade bookFacade;

    public Book get(String isbn) {
        return bookFacade.findById(isbn);
    }

    public void createOrUpdate(Book book) {
        bookFacade.createOrUpdate(book);
    }
}
