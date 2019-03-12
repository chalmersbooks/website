package controll;


import entity.Ad;
import org.primefaces.model.SortOrder;
import service.AdFacade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AdListController implements Serializable {

    @EJB
    private AdFacade adFacade;

    public List<Ad> getAds(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return adFacade.getAds(first, pageSize, sortField, sortOrder, filters);
    }

    public List<Ad> getAllAds() {
        return adFacade.findAll();
    }
}
