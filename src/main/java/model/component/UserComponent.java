package model.component;

import entity.Ad;
import entity.User;
import org.omnifaces.util.Faces;
import service.UserFacade;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@SessionScoped
public class UserComponent implements Serializable {

    @EJB
    private UserFacade userFacade;

    @Inject
    private SecurityContext securityContext;
    @Inject
    transient private Pbkdf2PasswordHash passwordHash;

    public User getUser() {
        if (isLoggedIn()) {
            String username = securityContext.getCallerPrincipal().getName();
            return userFacade.getUserById(username);
        }
        return null;
    }

    private boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        User u = userFacade.getUserById(securityContext.getCallerPrincipal().getName());
        String pwhash = u.getPassword();
        if(passwordHash.verify(oldPassword.toCharArray(), pwhash)){
            u.setPassword(passwordHash.generate(newPassword.toCharArray()));
            userFacade.createOrUpdate(u);
            return true;
        }
        return false;
    }

    public String loginUser(String username, String password) {
        return checkStatus(getStatus(username, password));
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) Faces.getContext()
                .getExternalContext()
                .getRequest();
    }

    private HttpServletResponse getHttpResponseFromFacesContext() {
        return (HttpServletResponse) Faces.getContext()
                .getExternalContext()
                .getResponse();
    }

    private AuthenticationStatus getStatus(String email, String password) {
        Credential credential = new UsernamePasswordCredential(email, new Password(password));

        AuthenticationStatus status = securityContext.authenticate(
                getHttpRequestFromFacesContext(),
                getHttpResponseFromFacesContext(),
                withParams().credential(credential));

        return status;
    }

    private String checkStatus(AuthenticationStatus status) {
        if (status.equals(SUCCESS)) {
            return "valid";
        } else if (status.equals(SEND_FAILURE)) {
            return null;
        }
        return null;
    }

    public User makeUser(String username, String password) {
        User user = new User();
        user.setPassword(passwordHash.generate(password.toCharArray()));
        user.setEmail(username);
        return user;
    }

    public void addAd(Ad ad){
        User u = getUser();
        u.addAd(ad);
        userFacade.createOrUpdate(u);
    }

}
