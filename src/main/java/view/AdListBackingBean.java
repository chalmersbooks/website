package view;

import entity.Ad;
import entity.CourseCode;
import entity.User;
import lombok.Data;
import org.primefaces.model.LazyDataModel;
import service.AdFacade;
import service.BookFacade;
import service.CourseCodeFacade;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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

    private List<Ad> ads;
    private Ad modalAd;
    private User user;
    private String searchTerm;
    private List<CourseCode> allCourseCodes;

    @PostConstruct
    public void init() {
        ads = adFacade.findAll();
    }

    public void filter(){
        if(searchTerm == null || searchTerm.length() == 0)
            ads = adFacade.findAll();
        else
            ads = adFacade.findBySearchTerm(searchTerm);
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
}