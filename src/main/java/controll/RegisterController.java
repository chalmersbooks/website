package controll;

import entity.User;
import model.bean.UserComponent;
import net.bootsfaces.utils.FacesMessages;
import org.omnifaces.util.Messages;
import service.UserFacade;
import view.RegisterBackingBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class RegisterController implements Serializable {

    @Inject
    private UserFacade userFacade;
    @Inject
    private UserComponent userComponent;
    @Inject
    private RegisterBackingBean backingBean;

    public String register() {
        if (!isRegistered()) {
            User user = userComponent.makeUser(
                    backingBean.getUsername(),
                    backingBean.getPassword()
            );
            userFacade.create(user);
            Messages.addGlobal(Messages.createInfo("User Created"));
            return "registered";
        } else {
            FacesMessages.fatal("CID already registered");
        }
        return null;
    }

    private boolean isRegistered() {
        return userFacade.getUserById(backingBean.getUsername()) != null;
    }
}
