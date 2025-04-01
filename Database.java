package SWE_Project_Files;

import java.util.*;

public class Database {
    // In-memory list of airports.
    public static List<Airport> airports = new ArrayList<>();
    // Mapping of each airport to its list of airplanes.
    public static Map<Airport, List<Airplane>> airportAirplanes = new HashMap<>();

    static {
        // ----- Create sample airports -----
        // Example: KJFK – John F. Kennedy International Airport (JET-A available)
        Map<String, Double> jfkFreq = new HashMap<>();
        jfkFreq.put("Tower", 119.1);
        Airport jfk = new Airport("KJFK", "John F. Kennedy International Airport", 40.6413, -73.7781,
                jfkFreq, new HashSet<>(Arrays.asList("JET-A")));
        
        // Example: KCAE – Columbia Metropolitan Airport (AVGAS available)
        Map<String, Double> caeFreq = new HashMap<>();
        caeFreq.put("Tower", 118.2);
        Airport cae = new Airport("KCAE", "Columbia Metropolitan Airport", 34.0007, -81.0348,
                caeFreq, new HashSet<>(Arrays.asList("AVGAS")));
        
        // Example: KLAX – Los Angeles International Airport (JET-A & AVGAS available)
        Map<String, Double> laxFreq = new HashMap<>();
        laxFreq.put("Tower", 120.9);
        Airport lax = new Airport("KLAX", "Los Angeles International Airport", 33.9416, -118.4085,
                laxFreq, new HashSet<>(Arrays.asList("JET-A", "AVGAS")));
        
        // Register the airports.
        airports.add(jfk);
        airports.add(cae);
        airports.add(lax);
        
        // ----- Create sample airplanes for the airports -----
        // For KJFK
        List<Airplane> jfkAirplanes = new ArrayList<>();
        jfkAirplanes.add(new Airplane("Boeing 737", AirplaneType.JET, 26000, 2500, 450));
        airportAirplanes.put(jfk, jfkAirplanes);
        
        // For KCAE
        List<Airplane> caeAirplanes = new ArrayList<>();
        caeAirplanes.add(new Airplane("Cessna 172", AirplaneType.PROP, 212, 36, 120));
        airportAirplanes.put(cae, caeAirplanes);
        
        // For KLAX
        List<Airplane> laxAirplanes = new ArrayList<>();
        laxAirplanes.add(new Airplane("Embraer E175", AirplaneType.JET, 12500, 1500, 410));
        laxAirplanes.add(new Airplane("Beechcraft King Air", AirplaneType.TURBOPROP, 3000, 400, 300));
        airportAirplanes.put(lax, laxAirplanes);
    }

    /**
     * Searches and returns a list of airports matching the query.
     * The query can be the exact ICAO or any substring of the full name.
     */
    public static List<Airport> searchAirports(String query) {
        List<Airport> result = new ArrayList<>();
        for (Airport a : airports) {
            if (a.icao.equalsIgnoreCase(query) ||
                a.name.toLowerCase().contains(query.toLowerCase())) {
                result.add(a);
            }
        }
        return result;
    }

    /**
     * Returns a single matching airport. If there are multiple results,
     * this method returns the first result. (In a complete GUI, you’d let the user choose.)
     */
    public static Airport selectAirport(String query) {
        List<Airport> matches = searchAirports(query);
        if (matches.isEmpty()) return null;
        if (matches.size() == 1) return matches.get(0);
        return matches.get(0);
    }
    
    /** Returns the airplanes available for a given airport. */
    public static List<Airplane> getAirplanesForAirport(Airport airport) {
        return airportAirplanes.getOrDefault(airport, new ArrayList<>());
    }

    /**
     * Finds a candidate airport to serve as a refuel stop.
     * The candidate must have the required fuel and be within maxRange.
     * Returns null if no candidate is found.
     */
    public static Airport findRefuelAirport(Airport current, Airport destination, double maxRange, Airplane airplane) {
        String requiredFuel = airplane.getRequiredFuel();
        Airport bestCandidate = null;
        double bestDistanceToDest = Double.MAX_VALUE;
        for (Airport candidate : airports) {
            if (candidate.equals(current) || candidate.equals(destination))
                continue;
            if (!candidate.fuelTypesAvailable.contains(requiredFuel))
                continue;
            double distanceFromCurrent = GeoUtils.calculateDistance(current, candidate);
            if (distanceFromCurrent <= maxRange) {
                double candidateToDest = GeoUtils.calculateDistance(candidate, destination);
                if (candidateToDest < bestDistanceToDest) {
                    bestCandidate = candidate;
                    bestDistanceToDest = candidateToDest;
                }
            }
        }
        return bestCandidate;
    }
}
