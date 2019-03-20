package controll;


import entity.Ad;
import entity.User;
import lombok.Data;
import lombok.extern.java.Log;
import service.AdFacade;
import service.UserFacade;
import view.AdListBackingBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Log
@Data
@Named
@RequestScoped
public class AdListController implements Serializable {

    @Inject
    private AdListBackingBean adListBackingBean;
    @Inject
    private UserFacade userFacade;
    @Inject
    private AdFacade adFacade;

    public void filter() {
        adListBackingBean.filter();
    }


    public void setAd(Ad ad) {
        adListBackingBean.setModalAd(ad);
        User u = userFacade.getUserById(ad.getUserId());
        adListBackingBean.setUser(u);
    }

    public void sort() {
        switch (adListBackingBean.getSortOn()) {
            case "1":
                adListBackingBean.setAds(
                        adFacade.findAndSortByPrice(
                                AdFacade.Order.ASC,
                                adListBackingBean.getSearchTerm()));
                break;
            case "2":
                adListBackingBean.setAds(
                        adFacade.findAndSortByPrice(
                                AdFacade.Order.DESC,
                                adListBackingBean.getSearchTerm()));
                break;
            case "3":
                adListBackingBean.setAds(
                        adFacade.findAndSortByDate(
                                AdFacade.Order.ASC,
                                adListBackingBean.getSearchTerm()));
                break;
            case "4":
                adListBackingBean.setAds(
                        adFacade.findAndSortByDate(
                                AdFacade.Order.DESC,
                                adListBackingBean.getSearchTerm()));
                break;
            default: // do nothing
                break;
        }

    }

}