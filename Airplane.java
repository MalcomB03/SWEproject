package SWE_Project_Files;

public class Airplane {
    public String makeModel;       // e.g. "Boeing 737"
    public AirplaneType type;
    public double fuelTankLiters;    // in liters
    public double fuelBurnRate;      // liters per hour
    public double cruiseSpeed;       // in knots

    public Airplane(String makeModel, AirplaneType type, double fuelTankLiters,
                    double fuelBurnRate, double cruiseSpeed) {
        this.makeModel = makeModel;
        this.type = type;
        this.fuelTankLiters = fuelTankLiters;
        this.fuelBurnRate = fuelBurnRate;
        this.cruiseSpeed = cruiseSpeed;
    }

    /** Determines which fuel is required based on airplane type */
    public String getRequiredFuel() {
        if (type == AirplaneType.PROP) {
            return "AVGAS";
        } else {
            // For JET and TURBOPROP
            return "JET-A";
        }
    }

    @Override
    public String toString() {
        return makeModel + " (" + type + ")";
    }
}
