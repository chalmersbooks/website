package utils;

import core.BookBuilder;
import entity.Book;
import entity.CourseCode;
import entity.User;
import service.BookFacade;
import service.CourseCodeFacade;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;

@Startup
@Singleton
public class StartupDummyApp {

    @EJB
    private UserFacade userFacade;
    @EJB
    private BookFacade bookFacade;
    @EJB
    private CourseCodeFacade ccFacade;
    @Inject
    private Pbkdf2PasswordHash passwordHash;


    @PostConstruct
    public void setup() {

        // TODO: Setup the whole application with dummy data here....


        addTestAccount();
        addTestBooks();
        addTestCourseCodes();
        addTestAds();


    }

    public void addTestAccount() {

        User u = new User();
        u.setEmail("test@test.com");
        u.setName("test testsson");
        /*u.setPassword("password");*/
        u.setPassword(passwordHash.generate("password".toCharArray()));

        userFacade.create(u);

    }

    public void addTestAds() {

    }

    public void addTestBooks() {

        for (int i = 0; i < 5; i++) {

            Book b = new BookBuilder()
                    .setISBN("1234" + i)
                    .setName("How to make a website 10" + i)
                    .setAuthor("Joakim")
                    .build();

            bookFacade.create(b);

        }
    }

    public void addTestCourseCodes() {

        List<Book> books = bookFacade.findAll();
        CourseCode cc = new CourseCode();
        cc.setCourseCode("DAT076");
        cc.setBooks(books);

        ccFacade.create(cc);

    }


}
