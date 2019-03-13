package view;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.EmailTypes;
import model.PasswordConstraints;

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

@Data
@Named
@ViewScoped
public class RegisterBackingBean implements Serializable {

    private Map<String, String> emails = new HashMap<>();
    private String CID;
    private String emailType;
    /*
    @Pattern(regexp = "^(?=.{8,}).*$", message = "Password must contain at least 8 character")
    @Pattern(regexp = "^(?=.*[a-z]).*$", message = "Password must contain at least one lower character")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase character")
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "Password must contain at least one number 0-9")
    @Pattern(regexp = "^(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one special character: @#$%^&+=")
    */
    private String password;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        EmailTypes[] re = EmailTypes.values();
        for (EmailTypes temp : re) {
            emails.put(temp.getType(), temp.getEmail());
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
        ((UIInput) comp).setValid(false);
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
        context.addMessage(comp.getClientId(context), fm);
    }

    private boolean isValid(Object password, String regex) {
        return password.toString().matches(regex);
    }

    public String getUsername() {
        return CID.concat(emailType);
    }
}
