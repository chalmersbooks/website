package controll;

import entity.Ad;
import entity.User;
import service.AdFacade;
import service.UserFacade;
import view.ProfileBackingBean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProfileController implements Serializable {

    @Inject
    private ProfileBackingBean pbb;

    @Inject
    private UserFacade userFacade;

    @Inject
    private AdFacade adFacade;


    public boolean changePassword() {
        if(pbb.getUserComponent().changePassword(pbb.getOldPassword(), pbb.getNewPassword())){
            reset();
            return true;
        }
        else{
            reset();
            return false;
        }
    }

    public void applyChanges() {
        User u = pbb.getUser();
        if(u.getName().length() != 0){
            String[] tmp = u.getName().split(" ");
            for(int i = 0; i < tmp.length; i++){
                tmp[i] = toPascal(tmp[i]);
            }
            StringBuilder sb = new StringBuilder();
            for(String s : tmp){
                sb.append(s);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length()-1);
            u.setName(sb.toString());
        }

        userFacade.createOrUpdate(u);
    }

    public void reset(){
        pbb.setNewPassword(null);
        pbb.setOldPassword(null);
    }

    private String toPascal(String s){
        StringBuilder sb = new StringBuilder();
        char[] a = s.toCharArray();
        sb.append(Character.toString(a[0]).toUpperCase());
        for(int i = 1; i < a.length; i++){
            sb.append(Character.toString(a[i]).toLowerCase());
        }
        return sb.toString();
    }

    public void delete(Ad ad){
        User u = pbb.getUser();
        u.getAds().remove(ad);
        userFacade.createOrUpdate(u);
        adFacade.delete(ad);
    }

    public void editAd(){
        Ad ad = pbb.getCurrentAd();
        adFacade.createOrUpdate(ad);
    }

    public void setAd(Ad ad){
        pbb.setCurrentAd(ad);
    }


}
