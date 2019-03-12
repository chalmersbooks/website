package login;


import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import java.io.Serializable;

@Named("logoutBean")
@SessionScoped
public class LogoutBean implements Serializable {

    @Inject
    private SecurityContext securityContext;

    public String logout() throws ServletException {
        if (securityContext.getCallerPrincipal() == null) {
            return null;
        } else {
            Faces.logout();
            return "login.xhtml";
        }

    }
}
