package login;

import lombok.extern.java.Log;
import org.omnifaces.util.Faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@ViewScoped
public class LogoutBean implements Serializable {

    public String logout() {
        Faces.getExternalContext().invalidateSession();
        //return "index.xhtml?faces-redirect=true";
        return "";
    }
}
