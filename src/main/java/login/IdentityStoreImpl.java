package login;

import entity.User;
import service.UserFacade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@RequestScoped
public class IdentityStoreImpl implements IdentityStore {

    @EJB
    private UserFacade userFacade;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        User user = getUser(credential.getCaller());
        if (validatePassword(user, credential.getPasswordAsString())) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }

    private User getUser(String caller) {
        return userFacade.getUserById(caller);
    }

    private boolean validatePassword(User storedUser, String givenPassword) {

        if (storedUser == null) {
            return false;
        }

        String storedHashedPassword = storedUser.getPassword();
        return passwordHash.verify(givenPassword.toCharArray(), storedHashedPassword);
    }


}
