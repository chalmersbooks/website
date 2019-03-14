package login;

import entity.User;
import lombok.extern.java.Log;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.RandomStringUtils;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.security.SecureRandom;

@Log
@ApplicationScoped
public class PendingAccountBean implements Serializable {

    @EJB
    private UserFacade userFacade;

    private BidiMap<User, String> accounts;

    @PostConstruct
    public void init() {
        accounts = new DualHashBidiMap<>();
    }

    public boolean isPending(String username) {
        User user = new User();
        user.setEmail(username);
        user.setPassword("");
        return accounts.containsKey(user);
    }

    public void activate(String hash) {
        User user = accounts.getKey(hash);
        userFacade.create(user);
    }

    public String getActivationHash(User user) {
        return accounts.get(user);
    }

    public void addToPending(User user) {
        accounts.put(user, createRegistrationHash());
    }

    private String createRegistrationHash() {
        String randomStr = makeRandomString();
        if (isAlreadyUsed(randomStr)) {
            return createRegistrationHash();
        }
        return randomStr;
    }

    private String makeRandomString() {
        int randomStrLength = 15;
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789").toCharArray();
        return RandomStringUtils.random(
                randomStrLength,
                0,
                possibleCharacters.length - 1,
                false,
                false,
                possibleCharacters,
                new SecureRandom());
    }

    private boolean isAlreadyUsed(String randomStr) {
        return accounts.getKey(randomStr) != null;
    }
}
