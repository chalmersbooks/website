package controll;

import entity.User;
import authorization.PendingAccountBean;
import lombok.extern.java.Log;
import model.component.UserComponent;
import org.omnifaces.util.Messages;
import service.UserFacade;
import utils.EmailClient;
import view.RegisterBackingBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.Serializable;

@Log
@Named
@RequestScoped
public class RegisterController implements Serializable {

    @Inject
    private UserFacade userFacade;
    @Inject
    private UserComponent userComponent;
    @Inject
    private RegisterBackingBean backingBean;
    @Inject
    private PendingAccountBean pendingAccountBean;
    @Inject
    private EmailClient emailClient;

    public String register() {
        if (isPending()) {
            Messages.addGlobalError("Cid is pending activations. Check your email.");
            return "";
        }
        if (isRegistered()) {
            Messages.addGlobalError("CID already registered.");
            return "";
        }

        User user = userComponent.makeUser(
                backingBean.getUsername().toLowerCase(),
                backingBean.getPassword()
        );

        pendingAccountBean.addToPending(user);
        sendRegistrationEmail(user);
        Messages.addGlobal(Messages.createInfo("User Created and added to pending. " +
                "Check your email."));
        return "registered";
    }

    private boolean isRegistered() {
        return userFacade.getUserById(backingBean.getUsername()) != null;
    }

    private boolean isPending() {
        return pendingAccountBean.isPending(backingBean.getUsername().toLowerCase());
    }

    private void sendRegistrationEmail(User user) {
        String to = user.getEmail();
        String registrationHash = pendingAccountBean.getActivationHash(user);
        String title = "Registration email";

        try {
            emailClient.sendEmail(to, title, createRegistrationMessage(registrationHash));
        } catch (MessagingException e) {
            log.info("Registration email was not sent. Some error occur");
        }
    }

    private String createRegistrationMessage(String registrationHash) {
        return "<h2>Press the link</h2><br/>" +
                "http://localhost:8080/chalmersbooks/authorize/" +
                registrationHash;
    }

    public String cancel() {
        return "cancel";
    }
}
