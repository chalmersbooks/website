package login;

import model.EmailTypes;
import model.PasswordConstraints;
import entity.User;
import lombok.Getter;
import lombok.Setter;
import net.bootsfaces.utils.FacesMessages;
import org.omnifaces.util.Messages;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    @EJB
    private UserFacade userFacade;
    @Inject
    transient private Pbkdf2PasswordHash passwordHash;

    @Getter
    @Setter
    private Map<String, String> emails = new HashMap<>();
    @Getter
    @Setter
    private String CID;
    @Getter
    @Setter
    private String emailType;
    @Getter
    @Setter
    /*
    @Pattern(regexp = "^(?=.{8,}).*$", message = "Password must contain at least 8 character")
    @Pattern(regexp = "^(?=.*[a-z]).*$", message = "Password must contain at least one lower character")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase character")
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "Password must contain at least one number 0-9")
    @Pattern(regexp = "^(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one special character: @#$%^&+=")
    */
    private String password;
    @Getter
    @Setter
    private String confirmPassword;


    @PostConstruct
    public void setup() {
        EmailTypes[] re = EmailTypes.values();
        for (EmailTypes temp : re) {
            emails.put(temp.getType(), temp.getEmail());
        }
    }

    public String register() {
        if (!isRegistered()) {
            userFacade.create(makeUser());
            Messages.addGlobal(Messages.createInfo("User Created"));
            return "registered";
        } else {
            FacesMessages.fatal("CID already registered");
        }
        return null;
    }

    private boolean isRegistered() {
        return userFacade.getUserById(getUsername()) != null;
    }

    private String getUsername() {
        return CID.concat(emailType);
    }

    private User makeUser() {
        User user = new User();
        user.setPassword(passwordHash.generate(password.toCharArray()));
        user.setEmail(getUsername());
        return user;
    }

    public void validatePassword(FacesContext context, UIComponent comp, Object value) {
        PasswordConstraints[] pc = PasswordConstraints.values();
        for (PasswordConstraints temp : pc) {
            if (!isValid(value, temp.getRegex())) {
                invalidPasswordMessage(context, comp, temp.getMessage());
                break;
            }
        }
    }

    private void invalidPasswordMessage(FacesContext context, UIComponent comp, String message) {
        ((UIInput) comp).setValid(false);
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
        context.addMessage(comp.getClientId(context), fm);
    }

    private boolean isValid(Object password, String regex) {
        return password.toString().matches(regex);
    }


}
