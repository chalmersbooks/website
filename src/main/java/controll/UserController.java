package controll;

import model.LoggedInUser;
import entity.User;
import service.UserFacade;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UserFacade userFacade;

    @Inject
    private SecurityContext securityContext;

    public LoggedInUser getUser() {
        if (isLoggedIn()) {
            String username = securityContext.getCallerPrincipal().getName();
            User user = userFacade.getUserById(username);
            return convertToLoggedIn(user);
        }
        return null;
    }

    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public LoggedInUser convertToLoggedIn(User user) {
        return new LoggedInUser(
                user.getEmail(),
                user.getName()
        );
    }

    public User convertToEntity(LoggedInUser user) {
        return userFacade.getUserById(user.getEmail());
    }

}
