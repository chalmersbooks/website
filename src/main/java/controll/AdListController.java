package controll;


import entity.Ad;
import entity.User;
import lombok.Data;
import service.UserFacade;
import view.AdListBackingBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Data
@Named
@RequestScoped
public class AdListController implements Serializable {

    @Inject
    private AdListBackingBean adListBackingBean;

    @Inject
    private UserFacade userFacade;

    public void filter(){
        adListBackingBean.filter();
    }


    public void setAd(Ad ad) {
        adListBackingBean.setModalAd(ad);
        User u = userFacade.getUserById(ad.getUserId());
        adListBackingBean.setUser(u);
    }

    public void apply(){
        Ad ad = adListBackingBean.getModalAd();
        System.out.println(ad.getPrice());
        adListBackingBean.getAdFacade().createOrUpdate(ad);
    }

}
