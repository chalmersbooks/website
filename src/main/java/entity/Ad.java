package entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Ad {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Book book;
    @OneToMany
    private List<CourseCode> courseCode;
    private int price;
    private LocalDateTime date;
    private String imageURL;
    @ManyToOne
    private User user;



}
