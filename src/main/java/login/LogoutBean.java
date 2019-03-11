package login;




import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.Serializable;

@Named("logoutBean")
@SessionScoped
public class LogoutBean implements Serializable {

    public String logout() throws ServletException {
        Faces.logout();
        return "login.xhtml";
    }
}
