package control;

import core.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import service.UserFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Log
@Named("indexer")
@ViewScoped
public class IndexBackingBean implements Serializable {

    /**
     * This bean is only for testing purposes
     */

    @EJB
    private UserFacade facade;

    @Setter
    private String soon = "Coming soon";

    public void addUser() {

        User user = new User();
        user.setName("Hello");
        user.setEmail("asd@asd");

        facade.create(user);

        log.info("Time to log");
        List<User> list = facade.findAll();
        for (User u : list) {
            log.info("We have a user: " + u.toString());
        }

    }

    public String getSoon() {
        if (facade == null) {
            return "its null....";
        }

        return "its not null!!! :D:D:D:D";
    }

}

