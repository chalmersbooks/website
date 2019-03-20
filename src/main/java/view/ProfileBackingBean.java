package view;

import entity.Ad;
import model.PasswordConstraints;
import model.component.UserComponent;
import entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import service.AdFacade;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Data
@ViewScoped
@Named
public class ProfileBackingBean implements Serializable {

    @Inject
    @Setter(AccessLevel.NONE)
    private UserComponent userComponent;

    private String oldPassword;
    private String newPassword;
    private User user;
    private List<Ad> ads;
    private Ad currentAd;
    private boolean disabled;


    @PostConstruct
    private void init(){
        user = userComponent.getUser();
        ads = user.getAds();
    }

    public void validatePassword(FacesContext context, UIComponent comp, Object value) {
        PasswordConstraints.validatePassword(context, comp, value);
    }

}

