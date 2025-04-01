package SWE_Project_Files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SplashScreen extends JFrame {
    public SplashScreen() {
        setTitle("Flight Planner - Splash Screen");
        setSize(500, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel disclaimer = new JLabel(
                "<html><center><h2>THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE</h2></center></html>",
                SwingConstants.CENTER);
        add(disclaimer, BorderLayout.CENTER);

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new FlightPlannerGUI().setVisible(true);
                    }
                });
            }
        });
        add(continueButton, BorderLayout.SOUTH);
    }
}
