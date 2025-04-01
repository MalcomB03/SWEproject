package SWE_Project_Files;

public class FlightPlanner {

    /**
     * Plans a flight from start to destination using the airplane's specifications.
     * It checks whether the direct distance exceeds the maximum range of the airplane.
     * If so, it adds refuel stops by finding suitable airports along the route.
     *
     * @param start The starting airport.
     * @param airplane The airplane to use.
     * @param destination The destination airport.
     * @return A FlightPlan containing one or more FlightLegs, or null if planning fails.
     */
    public FlightPlan planFlight(Airport start, Airplane airplane, Airport destination) {
        FlightPlan plan = new FlightPlan();
        // Maximum range in nautical miles
        double maxRange = (airplane.fuelTankLiters / airplane.fuelBurnRate) * airplane.cruiseSpeed;

        Airport current = start;
        // Loop: while the direct distance exceeds the airplane's maximum range, add refuel stops.
        while (GeoUtils.calculateDistance(current, destination) > maxRange) {
            // Find a refuel airport along the route.
            Airport stop = Database.findRefuelAirport(current, destination, maxRange, airplane);
            if (stop == null) {
                // No refueling stop available – flight is impossible.
                return null;
            }
            FlightLeg leg = createLeg(current, stop, airplane);
            plan.addLeg(leg);
            current = stop;
        }
        // Add the final leg.
        FlightLeg finalLeg = createLeg(current, destination, airplane);
        plan.addLeg(finalLeg);

        return plan;
    }

    private FlightLeg createLeg(Airport start, Airport dest, Airplane airplane) {
        double distance = GeoUtils.calculateDistance(start, dest);
        double heading = GeoUtils.calculateHeading(start, dest);
        double time = distance / airplane.cruiseSpeed; // time in hours
        return new FlightLeg(start, dest, heading, distance, time);
    }
}
