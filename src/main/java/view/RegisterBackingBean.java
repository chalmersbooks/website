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
        PasswordConstraints.validatePassword(context, comp, value);
    }

    public String getUsername() {
        return CID.concat(emailType);
    }
}
