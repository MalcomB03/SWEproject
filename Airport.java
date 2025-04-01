package SWE_Project_Files;
import java.util.*;

public class Airport {
    public String icao;         // e.g. "KJFK"
    public String name;         // e.g. "John F. Kennedy International Airport"
    public double latitude;     // in decimal degrees
    public double longitude;    // in decimal degrees
    public Map<String, Double> comFrequencies; // e.g., {"Tower": 119.1}
    public Set<String> fuelTypesAvailable;     // e.g., {"JET-A", "AVGAS"}

    public Airport(String icao, String name, double latitude, double longitude,
                   Map<String, Double> comFrequencies, Set<String> fuelTypesAvailable) {
        this.icao = icao;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comFrequencies = comFrequencies;
        this.fuelTypesAvailable = fuelTypesAvailable;
    }

    @Override
    public String toString() {
        return icao + " - " + name;
    }
}
