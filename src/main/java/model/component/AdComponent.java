package model.component;

import entity.Ad;
import javax.enterprise.context.Dependent;
import java.io.Serializable;

@Dependent
public class AdComponent implements Serializable {


    public boolean isCompleteAd(Ad ad) throws AdException {
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

}
