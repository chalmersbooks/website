package utils;

import core.AdBuilder;
import core.BookBuilder;
import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;
import service.AdFacade;
import service.BookFacade;
import service.CourseCodeFacade;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Startup
@Singleton
public class StartupDummyApp {

    @EJB
    private UserFacade userFacade;
    @EJB
    private BookFacade bookFacade;
    @EJB
    private CourseCodeFacade ccFacade;
    @EJB
    private AdFacade adFacade;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void setup() {

        // TODO: Setup the whole application with dummy data here....

        addTestAccount();
        addTestBooks();
        addTestCourseCodes();
        //addTestAds();
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
        List<CourseCode> courseCodes = new ArrayList<>();
        courseCodes.add(buildRandomCourseCode());

        Ad ad = new AdBuilder()
                .setPrice(generateRandomPrice())
                .setCourseCodes(courseCodes)
                .setBook(courseCodes.get(0).getBooks().get(0))
                .setUser(generateRandomUser())
                .build();

        adFacade.create(ad);
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

    private User generateRandomUser() {
        User user = new User();
        user.setEmail("slavnic@student.chalmers.se");
        user.setName("Sanjin");
        user.setPassword("password");
        userFacade.create(user);
        return user;
    }

    private CourseCode buildRandomCourseCode() {
        List<Book> books = new ArrayList<>();
        books.add(buildRandomBook());
        CourseCode cc = buildRandomCourseCode(books);
        ccFacade.create(cc);
        return cc;
    }

    private Book buildRandomBook() {
        Book book = new BookBuilder()
                .setISBN(generateRandomISBN())
                .setName(generateRandomName())
                .setAuthor("Joakim")
                .build();
        bookFacade.create(book);
        return book;
    }

    private CourseCode buildRandomCourseCode(List<Book> books) {
        CourseCode cc = new CourseCode();
        cc.setCourseCode(generateRandomCourseCode());
        cc.setBooks(books);
        return cc;
    }

    private String generateRandomISBN() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateRandomName() {
        return "Book " + (int) (Math.random() * 100);
    }

    private int generateRandomPrice() {
        return (int) (Math.random() * 100);
    }

    private String generateRandomCourseCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char c = (char) (65 + random.nextInt(25)); //ascii A-Z
            sb.append(c);
        }
        sb.append(random.nextInt(999));

        return sb.toString();
    }
}