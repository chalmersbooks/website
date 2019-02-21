package core;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserAccount {

    @Id
    private long id;
    private String name;
    private String email;

}
