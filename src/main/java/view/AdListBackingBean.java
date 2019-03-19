package view;

import controll.AdListController;
import entity.Ad;
import entity.CourseCode;
import entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.bean.UserComponent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import service.AdFacade;
import service.BookFacade;
import service.CourseCodeFacade;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Data
@Named
@ViewScoped
public class AdListBackingBean implements Serializable {

    @Inject
    private AdFacade adFacade;

    @Inject
    private BookFacade bookFacade;

    @Inject
    private CourseCodeFacade courseCodeFacade;

    private LazyDataModel lazyDataModel;
    private List<Ad> ads;
    private Ad modalAd;
    private User user;
    private String searchTerm;
    private List<CourseCode> allCourseCodes;

    @PostConstruct
    public void init() {
        //lazyDataModel = new LazyModel();
        ads = adFacade.findAll();
    }

    public void filter(){
        ads = adFacade.findContainingSearchTerm(searchTerm);
    }

    public List<String> completeText(String searchTerm){
        List<String> courseCodes = courseCodeFacade.findBySearchTerm(searchTerm.toUpperCase());
        List<String> books = bookFacade.findBySearchTerm(searchTerm);
        courseCodes.addAll(books);
        return courseCodes;
    }

    // For testing purposes
    public List<CourseCode> getAllCourseCodes(){
        return courseCodeFacade.findAll();
    }

    /*
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
    */
}