package utils;

import lombok.Data;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;

@Log
@Data
@ApplicationScoped
public class PropertyReader {

    private static final String PROPERTY_FILE = "/resources/login.properties";

    /**
     * These values are used for email client to connect to gmail account.
     * They are read from resources/login.properties
     */
    private String email;
    private String password;

    @PostConstruct
    public void init() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("login");
            email = bundle.getString("gmail.username");
            password = bundle.getString("gmail.password");
        } catch (MissingResourceException e) {
            log.log(Level.SEVERE, "login.properties must exist with gmail.username and gmail.password data.");
        }
    }
}
