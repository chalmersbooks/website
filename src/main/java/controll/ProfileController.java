package controll;

import entity.User;
import service.UserFacade;
import view.ProfileBackingBean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProfileController implements Serializable {

    @Inject
    private ProfileBackingBean pbb;

    @Inject
    private UserFacade userFacade;


    public boolean changePassword() {
        return pbb.getUserComponent().changePassword(pbb.getOldPassword(), pbb.getNewPassword());
    }

    public void applyChanges() {
        User u = pbb.getUser();
        userFacade.createOrUpdate(u);
    }
}
