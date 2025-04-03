import javax.swing.*;
import java.awt.*;


public class ProjectSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Main Project System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel warningLabel = new JLabel("THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE");
        warningLabel.setForeground(Color.RED);
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel welcomeLabel = new JLabel("Welcome to our program, what would you like to do first?");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton airportButton = new JButton("Manage Airports");
        JButton airplaneButton = new JButton("Manage Airplanes");
        JButton flightPlanButton = new JButton("Flight Planning System");

        airportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        airplaneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        flightPlanButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        airportButton.addActionListener(e -> new AirportsData());
        airplaneButton.addActionListener(e -> new ManageAirplanes());
        flightPlanButton.addActionListener(e -> new FlightPlannerGUI());

        panel.add(warningLabel);
        panel.add(welcomeLabel);
        panel.add(airportButton);
        panel.add(airplaneButton);
        panel.add(flightPlanButton);
        panel.add(Box.createVerticalStrut(5));

        add(panel);
        setVisible(true);
    }
}

class ManageAirplanes {
    public ManageAirplanes() {
        JOptionPane.showMessageDialog(null, "Managing Airplanes Module");
    }
}
