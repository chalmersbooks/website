package model;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public enum PasswordConstraints {
    MIN("^(?=.{8,}).*$", "Password must contain at least 8 character"),
    LOWERCASE("^(?=.*[a-z]).*$", "Password must contain at least one lower character"),
    UPPERCASE("^(?=.*[A-Z]).*$", "Password must contain at least one uppercase character"),
    NUMBER("^(?=.*[0-9]).*$", "Password must contain at least one number 0-9"),
    SPECIAL("^(?=.*[@#$%^&+=]).*$", "Password must contain at least one special character: @#$%^&+=");

    private String regex;
    private String message;

    PasswordConstraints(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public String getMessage() {
        return message;
    }

    public static void validatePassword(FacesContext context, UIComponent comp, Object value) {
        PasswordConstraints[] pc = PasswordConstraints.values();
        for (PasswordConstraints temp : pc) {
            if (!isValid(value, temp.getRegex())) {
                invalidPasswordMessage(context, comp, temp.getMessage());
                break;
            }
        }
    }

    private static void invalidPasswordMessage(FacesContext context, UIComponent comp, String message) {
        ((UIInput) comp).setValid(false);
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
        context.addMessage(comp.getClientId(context), fm);
    }

    private static boolean isValid(Object password, String regex) {
        return password.toString().matches(regex);
    }
}
