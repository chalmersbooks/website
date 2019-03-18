package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "User.findById", query = "SELECT user FROM User user WHERE user.email = :email")
@Data
@Entity
@EqualsAndHashCode(of = "email")
public class User {

    @Id
    private String email;
    private String name;
    @NotNull
    private String password;
    private String phoneNumber;
    private String address;
    @OneToMany
    private List<Ad> ads;

    public void addAd(Ad ad){
        if(ads == null)
            ads = new ArrayList<>();

        ads.add(ad);
    }

}
