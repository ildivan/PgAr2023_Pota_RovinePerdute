/**
 * Class for a vehicle that calculates cost based of x and y coordinates, ignoring altitude
 */
public class Tonatiuh implements EdgeCalculator {

    /**
     * Calculates the cost between two cities based on x and y coordinates.
     */
    @Override
    public double calculateEdgeDistance(City first, City second) {
        double deltaX = Math.pow(second.getX() - first.getX(), 2);
        double deltaY = Math.pow(second.getY() - first.getY(), 2);
        return Math.sqrt(deltaY + deltaX);
    }
}
