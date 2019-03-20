package model;

import entity.Book;
import entity.CourseCode;
import lombok.Data;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
@Data
public class MakingAd {

    private String courseCode;
    private Book book;
    private int price;
    private List<CourseCode> courseCodes;
    private List<String> showableCourseCodes;

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode.toUpperCase();
    }

    public void setShowableCourseCodes(List<String> codes) {
        List<String> toUpperCases = new ArrayList<>();
        for (String s : codes) {
            toUpperCases.add(s.toUpperCase());
        }
        showableCourseCodes = toUpperCases;
    }
}