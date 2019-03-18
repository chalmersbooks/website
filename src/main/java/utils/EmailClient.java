package utils;

import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * This class is inspired by:
 * https://www.logicbig.com/tutorials/java-ee-tutorial/java-mail/java-mail-quick-example.html
 */

@Log
@ApplicationScoped
public class EmailClient {

    @Inject
    private PropertyReader properties;

    private String email;
    private String password;

    @PostConstruct
    public void init() {
        email = properties.getEmail();
        password = properties.getPassword();
    }

    public void sendEmail(String to, String title, String msg) throws MessagingException {

        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, to, title, msg);

        Transport.send(message);
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");            //Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true"); //TLS must be activated
        props.put("mail.smtp.host", "smtp.gmail.com");  //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", "587");             //Outgoing port

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        return session;
    }

    private void prepareEmailMessage(MimeMessage message, String to, String title, String msg)
            throws MessagingException {
        message.setContent(msg, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    // TODO: Create a html message here showing who is sending etc...

}
