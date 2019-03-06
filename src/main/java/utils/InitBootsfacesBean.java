package utils;

import lombok.extern.java.Log;
import net.bootsfaces.component.ComponentsEnum;
import org.omnifaces.cdi.Eager;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.inject.Named;
import java.util.logging.Level;

/**
 * This class is a solution to problems with bootsfaces not rendering sometimes.
 * The solution is taken from:
 * https://www.beyondjava.net/running-bootsfaces-on-glassfish-or-payara
 * and is addressed in:
 * https://github.com/TheCoder4eu/BootsFaces-OSP/issues/732
 */

@Log
@Named
@Eager
@ApplicationScoped
public class InitBootsfacesBean {
    /**
     * Register the Bootsfaces renderers
     */
    private void initializeBootsfacesRenderers() {

        // Loop through all of the Bootsfaces components
        for (ComponentsEnum value : ComponentsEnum.values()) {

            // Get the component class information
            String className;

            // switchComponent has wrong classpath in ComponentsEnum
            if (value.name().equals("switchComponent")) {
                // Use correct qualified name
                className = "net.bootsfaces.component.switchComponent.Switch";
            } else {
                // Otherwise, use specified value
                className = value.classname();
            }

            // See if this is an internal reference
            if (className.contains("Internal")) {
                log.log(Level.INFO, "Init Bootsfaces: Skipping internal component: " + className);
            } else {

                log.log(Level.INFO, "Init Bootsfaces: Processing component: " + className);

                try {
                    // See if we can instantiate the class
                    Class componentClass = Class.forName(className);
                    Class<UIComponentBase> baseClass = componentClass.asSubclass(UIComponentBase.class);
                    UIComponentBase component = baseClass.newInstance();
                    String rendererFamily = component.getFamily();
                    String rendererType = component.getRendererType();

                    // Determine the renderer class name
                    String simpleName = componentClass.getSimpleName();
                    String rendererClassName;
                    switch (simpleName) {
                        case "NavCommandLink":
                            // Shares same renderer with NavLink
                            rendererClassName = "net.bootsfaces.component.navLink.NavLinkRenderer";
                            break;
                        default:
                            // We have to guess at the name of the renderer
                            rendererClassName = className + "Renderer";
                    }

                    // Look up the renderer
                    Class rendererSuperclass = Class.forName(rendererClassName);
                    Class<Renderer> rendererClass = rendererSuperclass.asSubclass(Renderer.class);
                    Renderer renderer = rendererClass.newInstance();
                    log.log(Level.INFO, "Init Bootsfaces: Registering renderer: " + rendererFamily + "/" + rendererType);

                    // ****** THIS IS THE IMPORTANT CALL TO REGISTER THE RENDERER *********
                    addRenderer(rendererFamily, rendererType, renderer);
                } catch (Throwable e) {
                    log.log(Level.WARNING, "Init Bootsfaces: Unable to register renderer for component: " + className, e);
                }
            }
        }
    }

    private RenderKit defaultRenderKit = getDefaultRenderKit();

    public RenderKit getDefaultRenderKit() {
        RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return renderKitFactory.getRenderKit(facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT);
    }

    public void addRenderer(String family, String rendererType, Renderer renderer) {
        defaultRenderKit.addRenderer(family, rendererType, renderer);
    }
}