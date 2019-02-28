package login;

import core.EmailTypes;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
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
    private String emailType;

    @PostConstruct
    public void setup() {
        EmailTypes[] re = EmailTypes.values();
        for (EmailTypes temp : re) {
            emails.put(temp.getType(), temp.getEmail());
        }
    }

    public void setEmailType(String emailType) {
        System.out.println("PRINTAR EMAIL TYPEN: " + emailType);
        this.emailType = emailType;
    }
}
