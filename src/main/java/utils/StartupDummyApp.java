package utils;

import entity.User;
import service.UserFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Startup
@Singleton
public class StartupDummyApp {

    @EJB
    private UserFacade userFacade;
    @Inject
    private Pbkdf2PasswordHash passwordHash;


    @PostConstruct
    public void setup() {

        // TODO: Setup the whole application with dummy data here....


    addTestAccount();
    addTestAds();



    }

    public void addTestAccount() {

        User u = new User();
        u.setEmail("test@test.com");
        u.setName("test testsson");
        /*u.setPassword("password");*/
        u.setPassword(passwordHash.generate("password".toCharArray()));

        userFacade.create(u);

    }

    public void addTestAds() {

    }




}
