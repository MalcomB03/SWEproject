package SWE_Project_Files;

import javax.swing.SwingUtilities;

public class FlightPlanningApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SplashScreen splash = new SplashScreen();
                splash.setVisible(true);
            }
        });
    }
}
