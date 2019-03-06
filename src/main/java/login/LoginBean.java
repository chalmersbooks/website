package login;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.bootsfaces.utils.FacesMessages;
import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static javax.security.enterprise.AuthenticationStatus.*;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue()
)
@Log
@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Getter
    @Setter
    @NotNull
    private String email;

    @Getter
    @Setter
    @NotNull
    private String password;

    public String login() {
        String status = checkStatus(getStatus());
        if (status == null || status.equals("")) {
            email = "";
            password = "";
            FacesMessages.fatal("Wrong email or password");
            return null;
        } else {
            return "index.xhtml?faces-redirect=true";
        }
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

    private AuthenticationStatus getStatus() {
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
}
