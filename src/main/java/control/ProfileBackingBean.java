package control;

import entity.User;
import lombok.Getter;
import lombok.Setter;
import service.UserFacade;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;

@Named("profile")
@ViewScoped
public class ProfileBackingBean implements Serializable {

    @EJB
    private UserFacade facade;

    @Inject
    private SecurityContext securityContext;

    @Getter
    @Setter
    private String oldPassword;
    @Getter
    @Setter
    private String newPassword;
    @Getter
    @Setter
    private String repeatPassword;


    private User getCurrent(){
        return facade.getUserById(securityContext.getCallerPrincipal().getName());
    }

    public String getName(){
        return getCurrent().getName();
    }

    public boolean changePassword(){
        //TODO current password is encrypted so comparing wont work.
        if(getCurrent().getPassword().equals(oldPassword)){
            getCurrent().setPassword(newPassword);
            return true;
        }else
            return false;
    }
}
