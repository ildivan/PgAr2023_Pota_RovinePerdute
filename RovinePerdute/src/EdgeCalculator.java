/**
 * Represents any class that can calculate the distance between two cities
 */
public interface EdgeCalculator {
    /**
     * Calculates the cost of traveling from a city to another, based on some concept of distance.
     */
    double calculateEdgeDistance(City first, City second);
}
