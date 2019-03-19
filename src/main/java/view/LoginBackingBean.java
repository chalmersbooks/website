package view;

import lombok.Data;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Named
@ViewScoped
public class LoginBackingBean implements Serializable {

    @NotNull
    private String email;
    @NotNull
    private String password;
    private String forgottenEmail;


}
