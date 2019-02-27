package login;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.omnifaces.util.Faces;
import org.omnifaces.util.FacesLocal;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@FacesConfig(
// Activates CDI build-in beans
        version = JSF_2_3
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue()
)
@Log
@Named
@RequestScoped
public class LoginBean {

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
        Credential credential = new UsernamePasswordCredential(email, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getHttpRequestFromFacesContext(),
                getHttpResponseFromFacesContext(),
                withParams().credential(credential));
        if (status.equals(SEND_CONTINUE)) {
            return "valid";
        } else if (status.equals(SEND_FAILURE)) {
            // TODO: Send message to screen that login was invalid
            log.info("THIS DOES NOT WORK!!!");
            Messages.addGlobalError( "Something went wrong");
            return "";
        } else {
            return null;
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


}
