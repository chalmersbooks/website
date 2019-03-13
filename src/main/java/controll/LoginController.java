package controll;

import lombok.extern.java.Log;
import model.bean.UserComponent;
import net.bootsfaces.utils.FacesMessages;
import view.LoginBackingBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private LoginBackingBean backingBean;
    @Inject
    private UserComponent userComponent;

    public String login() {
        String status = userComponent.loginUser(
                backingBean.getEmail(),
                backingBean.getPassword());
        if (status == null || status.equals("")) {
            backingBean.setEmail("");
            backingBean.setPassword("");
            FacesMessages.fatal("Wrong email or password");
            return null;
        } else {
            return "valid";
        }
    }

}
