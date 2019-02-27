package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@NamedQuery(name = "User.findByCid", query = "SELECT user FROM User user WHERE user.cid = :cid")
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @NotNull
    private String cid;
    @NotNull
    private String password;

}
