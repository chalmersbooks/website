package authorization;

import entity.User;
import lombok.extern.java.Log;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import service.UserFacade;
import utils.RandomStringGenerator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

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
        user.setEmail(username.toLowerCase());
        user.setPassword("");
        return accounts.containsKey(user);
    }

    public void activate(String hash) {
        User user = accounts.getKey(hash);
        userFacade.create(user);
        accounts.remove(user);
    }

    public String getActivationHash(User user) {
        return accounts.get(user);
    }

    public void addToPending(User user) {
        accounts.put(user, createRegistrationHash());
    }

    private String createRegistrationHash() {
        String randomStr = RandomStringGenerator.generateRandomString(15);
        if (isAlreadyUsed(randomStr)) {
            return createRegistrationHash();
        }
        return randomStr;
    }

    private boolean isAlreadyUsed(String randomStr) {
        return accounts.getKey(randomStr) != null;
    }
}
