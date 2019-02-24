package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    private long isbn;
    private String authors;
    private String name;
    private int edition;

}
