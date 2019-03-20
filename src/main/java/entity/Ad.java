package entity;

import lombok.Data;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Ad.findById", query = "SELECT ad FROM Ad ad WHERE ad.id = :id"),
        @NamedQuery(name = "Ad.findByUserId", query = "SELECT ad FROM Ad ad WHERE ad.userId = :userId"),
        @NamedQuery(name = "Ad.delete", query = "DELETE FROM Ad ad WHERE ad.id = :id"),
        @NamedQuery(name= "Ad.findAndSortByPriceDesc", query = "SELECT DISTINCT ad FROM Ad ad WHERE " +
                "ad.book.name LIKE CONCAT('%', :searchTerm, '%') OR ad.courseCodes IN" +
                "(SELECT cc FROM CourseCode cc WHERE cc.courseCode LIKE CONCAT('%', :searchTerm, '%')) " +
                "ORDER BY ad.price DESC"),
        @NamedQuery(name= "Ad.findAndSortByPriceAsc", query = "SELECT DISTINCT ad FROM Ad ad WHERE " +
                "ad.book.name LIKE CONCAT('%', :searchTerm, '%') OR ad.courseCodes IN" +
                "(SELECT cc FROM CourseCode cc WHERE cc.courseCode LIKE CONCAT('%', :searchTerm, '%')) " +
                "ORDER BY ad.price ASC"),
        @NamedQuery(name= "Ad.findAndSortByDateDesc", query = "SELECT DISTINCT ad FROM Ad ad WHERE " +
                "ad.book.name LIKE CONCAT('%', :searchTerm, '%') OR ad.courseCodes IN" +
                "(SELECT cc FROM CourseCode cc WHERE cc.courseCode LIKE CONCAT('%', :searchTerm, '%')) " +
                "ORDER BY ad.date DESC"),
        @NamedQuery(name= "Ad.findAndSortByDateAsc", query = "SELECT DISTINCT ad FROM Ad ad WHERE " +
                "ad.book.name LIKE CONCAT('%', :searchTerm, '%') OR ad.courseCodes IN" +
                "(SELECT cc FROM CourseCode cc WHERE cc.courseCode LIKE CONCAT('%', :searchTerm, '%')) " +
                "ORDER BY ad.date ASC")
})
public class Ad {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Book book;
    @ManyToMany
    private List<CourseCode> courseCodes;
    private int price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String imageURL;
    private String userId;

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private String formatedDate;

    public String getFormatedDate() {
        return dateFormatter.format(this.date);
    }

}