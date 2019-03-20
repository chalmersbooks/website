package controll;

import entity.User;
import lombok.extern.java.Log;
import model.component.UserComponent;
import net.bootsfaces.utils.FacesMessages;
import org.omnifaces.util.Messages;
import service.UserFacade;
import utils.EmailClient;
import utils.RandomStringGenerator;
import view.LoginBackingBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;

@Log
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue()
)
@Named
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private LoginBackingBean backingBean;
    @Inject
    private UserComponent userComponent;
    @Inject
    private UserFacade userFacade;
    @Inject
    private EmailClient emailClient;
    @Inject
    transient private Pbkdf2PasswordHash passwordHash;

    private User user;

    @PostConstruct
    private void init() {
        user = null;
    }

    public String login() {
        String status = userComponent.loginUser(
                backingBean.getEmail().toLowerCase(),
                backingBean.getPassword());
        if (status == null || status.equals("")) {
            backingBean.setEmail("");
            backingBean.setPassword("");
            FacesMessages.fatal("Wrong email or password");
            return null;
        } else {
            return "valid";
        }
    }

    public String register() {
        return "register";
    }

    public String lostPassword() {
        // TODO: Fix message
        Messages.addGlobalInfo("New password sent.");
        User user = userFacade.getUserById(backingBean.getEmail());
        if (isValidUser(user)) {
            this.user = user;
            changePasswordOnUser();
        } else {
            log.info("User was not found");
        }
        backingBean.setForgottenEmail("");
        return "";
    }

    private void changePasswordOnUser() {
        String newPassword = RandomStringGenerator.generateRandomString(10);
        user.setPassword(passwordHash.generate(newPassword.toCharArray()));
        userFacade.createOrUpdate(user);
        sendInformationEmail(newPassword);
    }

    private void sendInformationEmail(String newPassword) {
        try {
            emailClient.sendEmail(
                    user.getEmail(),
                    "Your new password",
                    lostPasswordMessage(newPassword));
        } catch (MessagingException me) {
            log.info("Was not able to send new password to email");
        }
    }

    private String lostPasswordMessage(String newPassword) {
        return "<h2>Hello!</h2>" +
                "<p>Your new password is: " + newPassword + "</p>" +
                "<p>Please make sure to change it asap</p>";
    }

    private boolean isValidUser(User user) {
        return user != null;
    }

}
