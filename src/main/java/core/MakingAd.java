package core;

import entity.Book;
import entity.CourseCode;
import lombok.Data;
import lombok.extern.java.Log;

import java.util.List;

@Log
@Data
public class MakingAd {

    private String courseCode;
    private Book book;
    private int price;
    private List<CourseCode> courseCodes;
    private String showableCourseCodes;

}
