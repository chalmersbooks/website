package controll;

import entity.Ad;
import model.bean.AdComponent;
import model.bean.AdException;
import org.omnifaces.util.Messages;
import service.AdFacade;

import javax.persistence.PersistenceException;
import java.io.Serializable;

public abstract class AdController implements Serializable {

    protected abstract Ad getAd();
    protected abstract AdComponent getAdComponent();
    protected abstract AdFacade getAdFacade();

    public String save() {
        try {
            Ad ad = getAd();
            getAdComponent().isCompleteAd(ad);
            getAdFacade().create(ad);
            Messages.addGlobal(Messages.createInfo("Add created"));
            return "saved";
        } catch (AdException | PersistenceException exception) {
            Messages.addGlobal(Messages.createError(exception.getMessage()));
        }
        return null;
    }
}
