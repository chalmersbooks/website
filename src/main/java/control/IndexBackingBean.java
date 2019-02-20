package control;

import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("indexer")
@ViewScoped
public class IndexBackingBean implements Serializable {

    @Getter
    @Setter
    private String soon = "Coming soon";

}

