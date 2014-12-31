/*
 * ClusteringUncertainDataApp.java
 */

package clusteringuncertaindata;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ClusteringUncertainDataApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
            show(new ClusteringUncertainDataView(this));
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ClusteringUncertainDataApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ClusteringUncertainDataApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ClusteringUncertainDataApp
     */
    public static ClusteringUncertainDataApp getApplication() {
        return Application.getInstance(ClusteringUncertainDataApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ClusteringUncertainDataApp.class, args);
    }
}
