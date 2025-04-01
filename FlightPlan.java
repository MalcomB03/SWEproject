package SWE_Project_Files;

import java.util.*;

public class FlightPlan {
    public java.util.List<FlightLeg> legs;

    public FlightPlan() {
        legs = new ArrayList<>();
    }

    public void addLeg(FlightLeg leg) {
        legs.add(leg);
    }

    @Override
    public String toString() {
        if (legs.isEmpty()) {
            return "No flight plan available.";
        }
        StringBuilder sb = new StringBuilder();
        for (FlightLeg leg : legs) {
            sb.append(leg.toString()).append("\n");
        }
        return sb.toString();
    }
}