package model;

import lombok.Data;

@Data
public class LoggedInUser {

    private String email;
    private String name;

    public LoggedInUser(String email, String name) {
        this.email = email;
        this.name = name;
    }


}
