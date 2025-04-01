package SWE_Project_Files;

public class FlightLeg {
    public Airport start;
    public Airport destination;
    public double heading; // in degrees
    public double distance; // in nautical miles
    public double time;     // in hours

    public FlightLeg(Airport start, Airport destination, double heading, double distance, double time) {
        this.start = start;
        this.destination = destination;
        this.heading = heading;
        this.distance = distance;
        this.time = time;
    }

    @Override
    public String toString() {
        // Use the first COM frequency if available.
        String startFreq = start.comFrequencies.isEmpty() ? "N/A" : start.comFrequencies.values().iterator().next().toString();
        String destFreq = destination.comFrequencies.isEmpty() ? "N/A" : destination.comFrequencies.values().iterator().next().toString();
        return String.format("Leg: %s -> %s\n   Heading: %.1f°\n   Distance: %.1f NM\n   Time: %.2f hr\n   COMs: %s & %s\n",
                start.name, destination.name, heading, distance, time, startFreq, destFreq);
    }
}