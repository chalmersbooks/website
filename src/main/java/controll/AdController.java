package controll;

import entity.Ad;
import model.component.AdComponent;
import model.component.AdException;
import model.component.UserComponent;
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
