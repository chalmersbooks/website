package view;

import entity.User;
import lombok.Setter;
import lombok.extern.java.Log;
import service.UserFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
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

    @Inject
    private SecurityContext securityContext;

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

    public boolean getUserLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public void logout() {
        log.info("LOGOUT MADDA FAKKA");
    }

}

