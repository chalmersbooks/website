package login;

import core.EmailTypes;
import core.PasswordConstraints;
import lombok.Getter;
import lombok.Setter;
import net.bootsfaces.utils.FacesMessages;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("registerBean")
@ViewScoped
public class RegisterBean implements Serializable {
    @Getter
    @Setter
    private Map<String, String> emails = new HashMap<>();

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

    public void register() {
        if (!verifiedPassword()) {
            FacesMessages.error("Error!", "Password do not match");
        } else {
            FacesMessages.info("Info", "Success!");
        }
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
        //((UIInput) comp).setValid(false);
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
        context.addMessage(comp.getClientId(context), fm);
    }

    private boolean isValid(Object password, String regex) {
        return password.toString().matches(regex);
    }

    private boolean verifiedPassword() {
        return password.equals(confirmPassword);
    }

}
