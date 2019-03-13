package controll;

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
    private UserComponent userComponent;

    public boolean changePassword(){
        return userComponent.changePassword(pbb.getOldPassword(), pbb.getNewPassword());
    }

    public void applyChanges(){
        userComponent.updateInfo(pbb.getUser());
    }
}
