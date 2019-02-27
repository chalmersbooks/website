package login;

import service.UserFacade;

import javax.ejb.EJB;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class IdentityStoreImpl implements IdentityStore {

    @EJB
    private UserFacade userFacade;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        String password = users.get(credential.getCaller());
        if (password != null && password.equals(credential.getPasswordAsString())) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }


}
