import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AirportsData {
    private static final Map<String, String> airportInfo = new HashMap<>();
    private static final Map<String, String> icaoToName = new HashMap<>();
    private static final Map<String, String> nameToIcao = new HashMap<>();
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JComboBox<String> startAirport;
    private static JTextArea startInfo;

    public AirportsData() {
        loadAirportData("airports.csv"); // Load airports from CSV
        SwingUtilities.invokeLater(AirportsData::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Electronic Flight Planning System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel startLabel = new JLabel("Search Airport Database:");
        startAirport = new JComboBox<>(icaoToName.keySet().toArray(new String[0]));
        startAirport.setEditable(true);

        startAirport.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterComboBox(startAirport, (String) startAirport.getEditor().getItem());
            }
        });

        startInfo = new JTextArea(5, 25);
        startInfo.setEditable(false);

        JButton addAirportButton = new JButton("Add New Airport");
        JButton modifyAirportButton = new JButton("Modify Airport");
        JButton deleteAirportButton = new JButton("Delete Airport");
        JButton displayAirportInfoButton = new JButton("Display Airport Info");

        // Update display logic to show full airport details
        startAirport.addActionListener(e -> {
            String selectedIcao = (String) startAirport.getSelectedItem();
            String airportName = icaoToName.get(selectedIcao);
            startInfo.setText(airportInfo.getOrDefault(airportName, "No data available"));
        });

        addAirportButton.addActionListener(e -> showAddAirportForm());
        modifyAirportButton.addActionListener(e -> showModifyAirportForm());
        deleteAirportButton.addActionListener(e -> deleteAirport());
        displayAirportInfoButton.addActionListener(e -> showAirportInfo());

        mainPanel.add(startLabel);
        mainPanel.add(startAirport);
        mainPanel.add(new JLabel("Airport Info:"));
        mainPanel.add(startInfo);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAirportButton);
        buttonPanel.add(modifyAirportButton);
        buttonPanel.add(deleteAirportButton);
        buttonPanel.add(displayAirportInfoButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void filterComboBox(JComboBox<String> comboBox, String query) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String airport : icaoToName.keySet()) {
            if (airport.toLowerCase().contains(query.toLowerCase())) {
                model.addElement(airport);
            }
        }
        String currentText = (String) comboBox.getEditor().getItem();
        comboBox.setModel(model);
        comboBox.getEditor().setItem(currentText);
        comboBox.showPopup();
    }

    private static void showAddAirportForm() {
        JPanel addPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        JTextField icaoField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField latField = new JTextField();
        JTextField lonField = new JTextField();
        JTextField freqTypeField = new JTextField();
        JTextField freqField = new JTextField();
        JTextField fuelField = new JTextField();

        addPanel.add(new JLabel("ICAO Code:"));
        addPanel.add(icaoField);
        addPanel.add(new JLabel("Airport Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Latitude:"));
        addPanel.add(latField);
        addPanel.add(new JLabel("Longitude:"));
        addPanel.add(lonField);
        addPanel.add(new JLabel("Comm Frequency Type:"));
        addPanel.add(freqTypeField);
        addPanel.add(new JLabel("Frequency (MHz):"));
        addPanel.add(freqField);
        addPanel.add(new JLabel("Fuel Type:"));
        addPanel.add(fuelField);

        int result = JOptionPane.showConfirmDialog(frame, addPanel, "Add New Airport", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String icao = icaoField.getText().trim();
            String name = nameField.getText().trim();
            String lat = latField.getText().trim();
            String lon = lonField.getText().trim();
            String freqType = freqTypeField.getText().trim();
            String freq = freqField.getText().trim();
            String fuel = fuelField.getText().trim();

            String airportName = icao + " - " + name;
            String info = "Name: " + name + "\nICAO: " + icao +
                    "\nLat: " + lat + ", Lon: " + lon +
                    "\nComms: " + freqType + " - " + freq + " MHz" +
                    "\nFuel: " + fuel;

            airportInfo.put(airportName, info);
            icaoToName.put(icao, airportName);
            nameToIcao.put(name, airportName);

            startAirport.addItem(icao);
            JOptionPane.showMessageDialog(frame, "Airport Added Successfully!");
        }
    }

    private static void showModifyAirportForm() {
        JPanel addPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        JTextField icaoField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField latField = new JTextField();
        JTextField lonField = new JTextField();
        JTextField freqTypeField = new JTextField();
        JTextField freqField = new JTextField();
        JTextField fuelField = new JTextField();

        addPanel.add(new JLabel("ICAO Code:"));
        addPanel.add(icaoField);
        addPanel.add(new JLabel("Airport Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Latitude:"));
        addPanel.add(latField);
        addPanel.add(new JLabel("Longitude:"));
        addPanel.add(lonField);
        addPanel.add(new JLabel("Comm Frequency Type:"));
        addPanel.add(freqTypeField);
        addPanel.add(new JLabel("Frequency (MHz):"));
        addPanel.add(freqField);
        addPanel.add(new JLabel("Fuel Type:"));
        addPanel.add(fuelField);

        int result = JOptionPane.showConfirmDialog(frame, addPanel, "Modify Airport by ICAO", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String icao = icaoField.getText().trim();
            String name = nameField.getText().trim();
            String lat = latField.getText().trim();
            String lon = lonField.getText().trim();
            String freqType = freqTypeField.getText().trim();
            String freq = freqField.getText().trim();
            String fuel = fuelField.getText().trim();

            String airportName = icao + " - " + name;
            String info = "Name: " + name + "\nICAO: " + icao +
                    "\nLat: " + lat + ", Lon: " + lon +
                    "\nComms: " + freqType + " - " + freq + " MHz" +
                    "\nFuel: " + fuel;

            airportInfo.put(airportName, info);
            icaoToName.put(icao, airportName);
            nameToIcao.put(name, airportName);

            startAirport.addItem(icao);
            JOptionPane.showMessageDialog(frame, "Airport Modified Successfully!");
        }
    }

    private static void deleteAirport() {
        String airportToDelete = (String) JOptionPane.showInputDialog(frame, "Select Airport to Delete:",
                "Delete Airport", JOptionPane.QUESTION_MESSAGE, null, icaoToName.keySet().toArray(), null);

        if (airportToDelete != null) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete " + airportToDelete + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String airportName = icaoToName.get(airportToDelete);
                airportInfo.remove(airportName);
                icaoToName.remove(airportToDelete);
                nameToIcao.values().remove(airportName);
                startAirport.removeItem(airportToDelete);
                JOptionPane.showMessageDialog(frame, "Airport Deleted Successfully!");
            }
        }
    }

    private static void showAirportInfo() {
        String airport = (String) JOptionPane.showInputDialog(frame, "Select Airport to View Info:",
                "Airport Info", JOptionPane.QUESTION_MESSAGE, null, icaoToName.keySet().toArray(), null);

        if (airport != null) {
            String airportName = icaoToName.get(airport);
            String info = airportInfo.getOrDefault(airportName, "No information available.");
            JOptionPane.showMessageDialog(frame, info, "Airport Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void loadAirportData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) { // Ensure sufficient data fields
                    String icao = data[0].trim();
                    String name = data[1].trim();
                    String lat = data[2].trim();
                    String lon = data[3].trim();
                    String freqType = data[4].trim();
                    String freq = data[5].trim();
                    String fuel = data[6].trim();

                    String airportName = icao + " - " + name;
                    String info = "Name: " + name + "\nICAO: " + icao +
                            "\nLat: " + lat + ", Lon: " + lon +
                            "\nComms: " + freqType + " - " + freq + " MHz" +
                            "\nFuel: " + fuel;

                    airportInfo.put(airportName, info);
                    icaoToName.put(icao, airportName);
                    nameToIcao.put(name, airportName);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading airport data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
