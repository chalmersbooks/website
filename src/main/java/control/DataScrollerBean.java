package control;

import entity.Ad;
import service.AdFacade;


import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named("dataScroller")
@ViewScoped
public class DataScrollerBean implements Serializable {

    @EJB
    private AdFacade adFacade;
    private List<Ad> ads;

    public List<Ad> getAds() {
        if (ads == null) {
            ads = adFacade.findAll();
        }
        return ads;
    }

    public void sortDate() {
        ads.sort(new Comparator<Ad>() {
            @Override
            public int compare(Ad a1, Ad a2) {
                return a1.getDate().compareTo(a2.getDate());
            }
        });
    }

    public void sortPrice() {
        ads.sort(new Comparator<Ad>() {
            @Override
            public int compare(Ad a1, Ad a2) {
                return Integer.compare(a1.getPrice(), a2.getPrice());
            }
        });
    }

}