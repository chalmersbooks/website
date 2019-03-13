package model;

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
}
