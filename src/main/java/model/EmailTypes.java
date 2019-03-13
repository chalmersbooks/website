package model;

public enum EmailTypes {
    STUDENT("Student", "@student.chalmers.se"),
    EMPLOYEE("Employee", "@chalmers.se"),
    ALUMNI("Alumni", "@alumni.chalmers.se");

    private String email;
    private String type;

    EmailTypes(String type, String email) {
        this.type = type;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }
}