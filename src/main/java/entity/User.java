package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@NamedQuery(name = "User.findById", query = "SELECT user FROM User user WHERE user.email = :email")
@Data
@Entity
public class User {

    @Id
    private String email;
    private String name;
    @NotNull
    private String password;

}
