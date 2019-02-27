package control;

import entity.Ad;
import entity.DummyBook;
import service.AdFacade;
import utils.DummyBookService;


import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
    private boolean SORTED = false;

    public List<Ad> getAds() {
        ads = adFacade.findAll();

        if (SORTED) {
            ads.sort(new Comparator<Ad>() {
                @Override
                public int compare(Ad a1, Ad a2) {
                    return Integer.compare(a1.getPrice(), a2.getPrice());
                }
            });
        }
        return ads;
    }

    public void sort() {
        SORTED = true;
    }
}
