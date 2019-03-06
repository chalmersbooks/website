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
import java.math.BigInteger;
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

    private final Random random = new Random();

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void setup() {

        // TODO: Setup the whole application with dummy data here....

        addTestAccount();
        //addTestBooks();
        //addTestCourseCodes();
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
        List<CourseCode> courseCodes;
        User user = buildRandomUser();
        int n = 25;
        for (int i = 0; i < n; i++) {
            courseCodes = new ArrayList<>();
            courseCodes.add(buildRandomCourseCode());
            Ad ad = new AdBuilder()
                    .setPrice(generateRandomPrice())
                    .setCourseCodes(courseCodes)
                    .setBook(courseCodes.get(0).getBooks().get(0))
                    .setUser(user)
                    .build();
            adFacade.create(ad);
        }
    }

    private User buildRandomUser() {
        User user = new User();
        user.setEmail("slavnic@student.chalmers.se");
        user.setName("Sanjin");
        user.setPassword(passwordHash.generate("password".toCharArray()));
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
        return String.valueOf(Math.abs(random.nextLong() / 1000000)); //13-digit
    }

    private String generateRandomName() {
        return "Encyclopedia volume " + (int) (Math.random() * 100);
    }

    private int generateRandomPrice() {
        return (int) (Math.random() * 100);
    }

    private String generateRandomCourseCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char c = (char) (65 + random.nextInt(25)); //ascii A-Z
            sb.append(c);
        }
        sb.append(random.nextInt(999));

        return sb.toString();
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