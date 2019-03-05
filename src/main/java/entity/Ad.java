package entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
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

    public void addCourseCode(CourseCode code) {
        if (!courseCodes.contains(code)) {
            courseCodes.add(code);
        }
    }


}
