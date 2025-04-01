package SWE_Project_Files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FlightPlannerGUI extends JFrame {
    private JTextField txtStartAirport;
    private JTextField txtDestinationAirport;
    private JComboBox<Airplane> cbAirplanes;
    private JButton btnLoadAirplanes;
    private JButton btnPlanFlight;
    private JTextArea taFlightPlan;

    private Airport selectedStartAirport;

    public FlightPlannerGUI() {
        setTitle("Electronic Flight Planning System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Top panel for input fields and buttons.
        JPanel pnlTop = new JPanel(new GridLayout(4, 2, 5, 5));
        
        pnlTop.add(new JLabel("Enter Starting Airport (ICAO or name substring):"));
        txtStartAirport = new JTextField();
        pnlTop.add(txtStartAirport);

        pnlTop.add(new JLabel("Enter Destination Airport (ICAO or name substring):"));
        txtDestinationAirport = new JTextField();
        pnlTop.add(txtDestinationAirport);

        // Button to load airplanes for the starting airport.
        btnLoadAirplanes = new JButton("Load Airplanes for Start Airport");
        btnLoadAirplanes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAirplanes();
            }
        });
        pnlTop.add(btnLoadAirplanes);

        // Combo box to select an available airplane.
        cbAirplanes = new JComboBox<>();
        pnlTop.add(cbAirplanes);

        // Button to trigger flight planning.
        btnPlanFlight = new JButton("Plan Flight");
        btnPlanFlight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planFlight();
            }
        });
        pnlTop.add(btnPlanFlight);

        add(pnlTop, BorderLayout.NORTH);

        // Text area to display the computed flight plan.
        taFlightPlan = new JTextArea();
        taFlightPlan.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taFlightPlan);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAirplanes() {
        String startQuery = txtStartAirport.getText().trim();
        if (startQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a starting airport.");
            return;
        }
        selectedStartAirport = Database.selectAirport(startQuery);
        if (selectedStartAirport == null) {
            JOptionPane.showMessageDialog(this, "No airport found for: " + startQuery);
            return;
        }
        List<Airplane> airplanes = Database.getAirplanesForAirport(selectedStartAirport);
        if (airplanes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No airplanes found for " + selectedStartAirport.name);
            return;
        }
        cbAirplanes.removeAllItems();
        for (Airplane plane : airplanes) {
            cbAirplanes.addItem(plane);
        }
        JOptionPane.showMessageDialog(this, "Airplanes loaded for " + selectedStartAirport.name);
    }

    private void planFlight() {
        if (selectedStartAirport == null) {
            JOptionPane.showMessageDialog(this, "Please load the starting airport and airplanes first.");
            return;
        }
        String destQuery = txtDestinationAirport.getText().trim();
        if (destQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a destination airport.");
            return;
        }
        Airport destinationAirport = Database.selectAirport(destQuery);
        if (destinationAirport == null) {
            JOptionPane.showMessageDialog(this, "No airport found for: " + destQuery);
            return;
        }
        Airplane airplane = (Airplane) cbAirplanes.getSelectedItem();
        if (airplane == null) {
            JOptionPane.showMessageDialog(this, "Please select an airplane.");
            return;
        }
        FlightPlanner planner = new FlightPlanner();
        FlightPlan flightPlan = planner.planFlight(selectedStartAirport, airplane, destinationAirport);
        if (flightPlan == null) {
            taFlightPlan.setText("Flight planning failed: The flight is impossible given the refuel constraints.");
        } else {
            taFlightPlan.setText(flightPlan.toString());
        }
    }
}
