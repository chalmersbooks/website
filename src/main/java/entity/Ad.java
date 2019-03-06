package entity;

import lombok.Data;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NamedQuery(name = "Ad.findByName", query = "SELECT ad FROM Ad ad WHERE ad.user.email = :name")
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
    /*// TODO: User here is temporary. Cascade should not be as this. Only for testing purposes.
    @ManyToOne(cascade = CascadeType.ALL)*/
    @ManyToOne
    private User user;

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private String formatedDate;

    public void addCourseCode(CourseCode code) {
        if (!courseCodes.contains(code)) {
            courseCodes.add(code);
        }
    }

    public String getFormatedDate() {
        return dateFormatter.format(this.date);
    }

}
