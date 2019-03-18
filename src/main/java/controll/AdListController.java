package controll;


import entity.Ad;
import entity.User;
import lombok.Data;
import model.bean.UserComponent;
import org.primefaces.model.SortOrder;
import service.AdFacade;
import service.UserFacade;
import view.AdListBackingBean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Named
@RequestScoped
public class AdListController implements Serializable {

    @Inject
    private AdListBackingBean adListBackingBean;

    @Inject
    private UserFacade userFacade;

    private Ad ad;


    public void setAd(Ad ad){
        System.out.println(ad.getUserId());
        User u = userFacade.getUserById(ad.getUserId());
        adListBackingBean.setUser(u);
    }

}
