package core;

import entity.Book;
import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class MakingAd {

    private String courseCode;
    private Book book;

    public void setBook(Book book) {
        log.info("A books is placed here... " + book.toString());
        this.book = book;
    }

}
