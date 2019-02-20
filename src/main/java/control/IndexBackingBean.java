package control;

import core.User;
import lombok.Getter;
import lombok.Setter;
import service.UserFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("indexer")
@ViewScoped
public class IndexBackingBean implements Serializable {

    @EJB
    private UserFacade facade;

    @Setter
    private String soon = "Coming soon";

    public void addUser() {
        User user = new User();
        user.setId(123);
        user.setName("Hello");
        user.setEmail("asd@asd");

        facade.create(user);

    }

    public String getSoon() {
        if (facade == null) {
            return "its null....";
        }

        return "its not null!!! :D:D:D:D";
    }

}

