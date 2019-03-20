package controll;

import entity.Ad;
import lombok.Getter;
import lombok.extern.java.Log;
import model.component.AdComponent;
import org.primefaces.event.FlowEvent;
import view.AdWizardBackingBean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Log
@Named
@ViewScoped
public class AdWizardController extends AdController {

    @Inject
    private AdWizardBackingBean backingBean;
    @Inject
    @Getter
    private AdComponent adComponent;

    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();

        log.info("Next step is -> " + nextStep);
        if (nextStep.equals("book")) {
            backingBean.setFoundBooks();
        } else if (nextStep.equals("information")) {
            backingBean.setInformationFields();
        }
        return nextStep;
    }

    @Override
    protected Ad getAd() {
        return backingBean.makeAd();
    }
}
