package controll;

import entity.Ad;
import entity.User;
import model.bean.AdComponent;
import model.bean.AdException;
import model.bean.UserComponent;
import org.omnifaces.util.Messages;
import service.AdFacade;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.io.Serializable;

public abstract class AdController implements Serializable {

    @Inject
    private UserComponent userComponent;

    @Inject
    private AdFacade getAdFacade;

    protected abstract Ad getAd();

    protected abstract AdComponent getAdComponent();

    public String save() {
        try {
            Ad ad = getAd();
            getAdComponent().isCompleteAd(ad);
            getAdFacade.create(ad);
            Messages.addGlobal(Messages.createInfo("Add created"));
            return "saved";
        } catch (AdException | PersistenceException exception) {
            Messages.addGlobal(Messages.createError(exception.getMessage()));
        }
        return null;
    }

    public void addAdToUser(Ad ad) {
        userComponent.addAd(ad);
    }
}
