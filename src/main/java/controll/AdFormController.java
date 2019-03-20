package controll;

import entity.Ad;
import lombok.Getter;
import model.component.AdComponent;
import view.AdFormBackingBean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdFormController extends AdController {

    @Inject
    private AdFormBackingBean backingBean;
    @Inject
    @Getter
    private AdComponent adComponent;

    protected Ad getAd() {
        return backingBean.makeAd();
    }

}
