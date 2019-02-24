package entity;

import com.sun.istack.internal.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Ad {

/*
    TODO: Vi vill inte ha Cascade.ALL för om vi tar bort en add tas allt annat bort också...
 */

    @Id
    @GeneratedValue
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Book book;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CourseCode> courseCodes;
    private int price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String imageURL;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public void addCourseCode(CourseCode code) {
        if (!courseCodes.contains(code)) {
            courseCodes.add(code);
        }
    }


}
