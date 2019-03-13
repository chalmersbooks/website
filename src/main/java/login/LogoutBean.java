package login;

import lombok.extern.java.Log;
import org.omnifaces.util.Faces;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.Serializable;

@Log
@Named
@ViewScoped
public class LogoutBean implements Serializable {

    public String logout() {
        try {
            Faces.logout();
        } catch (ServletException se) {
            log.info("Something went wrong when logging out");
        }
        return null;
    }
}
