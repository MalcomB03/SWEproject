package SWE_Project_Files;

public class GeoUtils {
    // Earth's radius in nautical miles
    private static final double EARTH_RADIUS_NM = 3440.06479; 

    /** Computes the haversine (great circle) distance between two airports in nautical miles */
    public static double calculateDistance(Airport a, Airport b) {
        double lat1 = Math.toRadians(a.latitude);
        double lat2 = Math.toRadians(b.latitude);
        double lon1 = Math.toRadians(a.longitude);
        double lon2 = Math.toRadians(b.longitude);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double haversine = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
        return EARTH_RADIUS_NM * c;
    }

    /** Calculates the true heading (initial bearing) from airport A to B in degrees */
    public static double calculateHeading(Airport a, Airport b) {
        double lat1 = Math.toRadians(a.latitude);
        double lat2 = Math.toRadians(b.latitude);
        double dLon = Math.toRadians(b.longitude - a.longitude);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) -
                   Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double initialBearing = Math.atan2(y, x);
        double bearingDegrees = Math.toDegrees(initialBearing);
        // Normalize to 0...360 degrees
        return (bearingDegrees + 360) % 360;
    }
}
