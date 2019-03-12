package controll;

import core.AdBuilder;
import entity.Ad;
import service.AdFacade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import java.io.Serializable;

@Named
@RequestScoped
public class AdController implements Serializable {

    @EJB
    private AdFacade adFacade;

    public Ad createAd(AdBuilder completeBuilder) throws AdException {
        return createAd(completeBuilder.build());
    }

    public Ad createAd(Ad ad) throws AdException {
        try {
            isCompleteAd(ad);
            save(ad);
            return ad;
        } catch (AdException ae) {
            return null;
        }
    }

    private boolean isCompleteAd(Ad ad) throws AdException {
        if (ad.getBook() == null) {
            throw new AdException("Missing book");
        }
        if (ad.getPrice() <= 0) {
            throw new AdException("Price not valid");
        }
        if (ad.getCourseCodes() == null || ad.getCourseCodes().size() == 0) {
            throw new AdException("Missing course codes");
        }

        return true;
    }

    private void save(Ad ad) throws AdException {
        try {
            adFacade.create(ad);
        } catch (PersistenceException pe) {
            throw new AdException(pe.getMessage());
        }
    }

}
