package entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Ad.findByName", query = "SELECT ad FROM Ad ad WHERE ad.userId = :name"),
        @NamedQuery(name = "Ad.delete", query = "DELETE FROM Ad ad WHERE ad.id = :id")
})
public class Ad {

    // TODO: Replace AdBuilder with builder from this...

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
