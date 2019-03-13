package controll;

import entity.User;
import service.UserFacade;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;

@SessionScoped
public class UserComponent implements Serializable {

    @EJB
    private UserFacade userFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    transient private Pbkdf2PasswordHash passwordHash;

    public User getUser() {
        if (isLoggedIn()) {
            String username = securityContext.getCallerPrincipal().getName();
            return userFacade.getUserById(username);
        }
        return null;
    }

    private boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }


    public boolean changePassword(String oldPassword, String newPassword){
        User u = userFacade.getUserById(securityContext.getCallerPrincipal().getName());
        String pwhash = u.getPassword();
        if(passwordHash.verify(oldPassword.toCharArray(), pwhash)){
            u.setPassword(passwordHash.generate(newPassword.toCharArray()));
            userFacade.createOrUpdate(u);
            return true;
        }
        return false;
    }

    public void updateInfo(User u){
        User tmp = userFacade.getUserById(securityContext.getCallerPrincipal().getName());
        tmp.setAll(u);
    }

}
