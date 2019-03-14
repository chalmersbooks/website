package view;

import lombok.extern.java.Log;
import model.bean.UserComponent;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Named
@ViewScoped
public class TemplateBackingBean implements Serializable {

    @Inject
    private UserComponent userComponent;

    public boolean isLoggedIn() {
        return userComponent.getUser() != null;
    }
}
