package view;

import controll.UserComponent;
import entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Data
@ViewScoped
@Named("profile")
public class ProfileBackingBean implements Serializable {

    @Inject
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private UserComponent userComponent;

    private String oldPassword;
    private String newPassword;
    private User user;
    private boolean disabled;


    @PostConstruct
    private void init(){
        user = userComponent.getUser();
    }

    public void setDisabled(){

    }

}

