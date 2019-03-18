package model;

import entity.Ad;
import entity.Book;
import entity.CourseCode;
import entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdBuilder {

    private Ad ad;

    public AdBuilder() {
        ad = new Ad();
        ad.setCourseCodes(new ArrayList<>());
    }

    public AdBuilder setCourseCodes(List<CourseCode> courseCode) {
        ad.setCourseCodes(courseCode);
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

    public AdBuilder setUser(String userId) {
        ad.setUserId(userId);
        return this;
    }

    public Ad build() {
        ad.setDate(new Date());
        return ad;
    }
}

