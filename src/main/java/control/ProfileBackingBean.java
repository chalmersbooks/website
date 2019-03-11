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
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;

@Named("profile")
@ViewScoped
public class ProfileBackingBean implements Serializable {

    @EJB
    private UserFacade facade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    transient private Pbkdf2PasswordHash passwordHash;

    @Getter
    @Setter
    private String oldPassword;
    @Getter
    @Setter
    private String newPassword;
    @Getter
    @Setter
    private String repeatPassword;
    @Setter
    private String userName;


    private User getCurrent(){
        return facade.getUserById(securityContext.getCallerPrincipal().getName());
    }

    public String getUserName(){
        return getCurrent().getName();
    }


    public boolean changePassword(){
        String pwhash = getCurrent().getPassword();
        if(passwordHash.verify(oldPassword.toCharArray(), pwhash)){
            getCurrent().setPassword(passwordHash.generate(newPassword.toCharArray()));
            facade.edit(getCurrent());
            return true;
        }
        return false;
    }

    public void applyChanges(){
        System.out.println(userName);
        getCurrent().setName(userName);
        facade.edit(getCurrent());
    }
}
