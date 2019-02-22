package core;

import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;

public class AdBuilder {

    private Ad ad;

    public AdBuilder() {
        ad = new Ad();
    }

    public AdBuilder setCourseCode(CourseCode courseCode) {
        ad.addCourseCode(courseCode);
        return this;
    }

    public AdBuilder setBook(Book book) {
        ad.setBook(book);
        return this;
    }

    public AdBuilder setPrice(int price) {
        ad.setPrice(price);
        return this;
    }

    public AdBuilder setUser(User user) {
        ad.setUser(user);
        return this;
    }

    public Ad build() {
        return ad;
    }
}

