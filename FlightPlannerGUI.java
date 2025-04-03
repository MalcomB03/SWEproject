import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.event.*;

public class FlightPlannerGUI {
    private static final Map<String, String> airportInfo = new HashMap<>();
    private static final Map<String, String> icaoToName = new HashMap<>();
    private static final Map<String, String> nameToIcao = new HashMap<>();
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JComboBox<String> startAirport;
    private static JComboBox<String> destinationAirport;
    private static JTextArea startInfo;
    private static JTextArea destinationInfo;
    private static JToggleButton searchModeToggle;

    public FlightPlannerGUI() {
        loadAirportData("airports.csv"); // Load airports from CSV
        SwingUtilities.invokeLater(FlightPlannerGUI::createAndShowGUI);
    }
    

    private static void createAndShowGUI() {
        frame = new JFrame("Electronic Flight Planning System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));

        searchModeToggle = new JToggleButton("Search by ICAO");
        searchModeToggle.addItemListener(e -> updateSearchMode());

        JLabel startLabel = new JLabel("Starting Airport:");
        startAirport = new JComboBox<>(icaoToName.keySet().toArray(new String[0]));
        startAirport.setEditable(true);  // Allow typing
        startInfo = new JTextArea(3, 25);
        startInfo.setEditable(false);

        JLabel destinationLabel = new JLabel("Destination Airport:");
        destinationAirport = new JComboBox<>(icaoToName.keySet().toArray(new String[0]));
        destinationAirport.setEditable(true);  // Allow typing
        destinationInfo = new JTextArea(3, 25);
        destinationInfo.setEditable(false);

        JLabel aircraftLabel = new JLabel("Aircraft Type:");
        JComboBox<String> aircraftType = new JComboBox<>(new String[]{"Small Prop Plane", "Jet", "Multi-engine Jet"});

        JButton planFlightButton = new JButton("Plan Flight");

        startAirport.addActionListener(e -> startInfo.setText(airportInfo.getOrDefault((String) startAirport.getSelectedItem(),
         "No data")));
        destinationAirport.addActionListener(e -> destinationInfo.setText(airportInfo.getOrDefault((String) destinationAirport.getSelectedItem(),
         "No data")));

        planFlightButton.addActionListener(e -> showFlightOptions());

        // Add document listeners for search functionality
        addSearchFunctionality(startAirport, icaoToName.keySet());
        addSearchFunctionality(destinationAirport, icaoToName.keySet());

        mainPanel.add(searchModeToggle);
        mainPanel.add(new JLabel()); // Spacer
        mainPanel.add(startLabel);
        mainPanel.add(startAirport);
        mainPanel.add(new JLabel("Airport Info:"));
        mainPanel.add(startInfo);
        mainPanel.add(destinationLabel);
        mainPanel.add(destinationAirport);
        mainPanel.add(new JLabel("Airport Info:"));
        mainPanel.add(destinationInfo);
        mainPanel.add(aircraftLabel);
        mainPanel.add(aircraftType);
        mainPanel.add(new JLabel()); // Spacer
        mainPanel.add(planFlightButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void addSearchFunctionality(JComboBox<String> comboBox, Set<String> items) {
        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> filterComboBoxItems(comboBox, editor.getText(), items));
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> filterComboBoxItems(comboBox, editor.getText(), items));
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> filterComboBoxItems(comboBox, editor.getText(), items));
            }
        });
    }

    private static void filterComboBoxItems(JComboBox<String> comboBox, String text, Set<String> items) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        // Filter items based on text
        List<String> filteredItems = items.stream()
                .filter(item -> item.toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        if (filteredItems.isEmpty()) {
            model.addElement("No results found");
        } else {
            for (String item : filteredItems) {
                model.addElement(item);
            }
        }
        // Revalidate and repaint the combo box to show updated items
        comboBox.setModel(model);
        comboBox.setEditable(true);  // Preserve ability to type
        comboBox.setPopupVisible(true);;  // Ensure the dropdown is visible
        comboBox.revalidate();
        comboBox.repaint();
    }

    private static void updateSearchMode() {
        boolean searchByICAO = searchModeToggle.isSelected();
        searchModeToggle.setText(searchByICAO ? "Search by Name" : "Search by ICAO");

        Set<String> newItems = searchByICAO ? icaoToName.keySet() : nameToIcao.keySet();

        // Preserve selected values
        String startSelection = (String) startAirport.getSelectedItem();
        String destinationSelection = (String) destinationAirport.getSelectedItem();

        updateComboBox(startAirport, newItems);
        updateComboBox(destinationAirport, newItems);

        startAirport.setSelectedItem(startSelection);
        destinationAirport.setSelectedItem(destinationSelection);
    }

    private static void updateComboBox(JComboBox<String> comboBox, Set<String> items) {
        comboBox.removeAllItems();
        for (String item : items) {
            comboBox.addItem(item);
        }
    }

    private static void showFlightOptions() {
        JPanel flightOptionsPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Flight Options Available", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        flightOptionsPanel.add(title, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(mainPanel);
            frame.revalidate();
            frame.repaint();
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        flightOptionsPanel.add(topPanel, BorderLayout.WEST);

        frame.getContentPane().removeAll();
        frame.add(flightOptionsPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void loadAirportData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String icao = data[0];
                    String name = data[1];
                    String airportName = icao + " - " + name;
                    String info = "Lat: " + data[2] + ", Lon: " + data[3] +
                            "\nComm: " + data[4] +
                            "\nFuel: " + data[5];
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
