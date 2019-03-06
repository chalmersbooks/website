package view;

import controll.AdListController;
import entity.Ad;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import service.AdFacade;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class AdListBackingBean implements Serializable {

    @Inject
    AdListController adListController;

    @Getter
    @Setter
    private LazyDataModel lazyDataModel;

    @Getter
    @Setter
    private List<Ad> ads;

    @PostConstruct
    public void init() {
        lazyDataModel = new LazyModel();
    }


    private class LazyModel extends LazyDataModel<Ad> {


        @Override
        public List<Ad> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            List<Ad> ads = adListController.getAds(first, pageSize, sortField, sortOrder, filters);

            System.out.println("first : " + first);
            System.out.println("pageSize : " + pageSize);
            System.out.println("getPageSize : " + getPageSize());
            System.out.println("getRowCount : " + getRowCount());
            this.setRowCount(pageSize);

            return ads;
        }
    }
}