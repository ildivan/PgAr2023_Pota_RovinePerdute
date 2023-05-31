/**
 * Class for a vehicle that calculates cost based of the altitude, or h value
 */
public class Metztli implements EdgeCalculator {

    /**
     * Calculates the cost between two cities based on h value
     */
    @Override
    public double calculateEdgeDistance(City first, City second) {
        double fuel = second.getH() - first.getH();
        return Math.abs(fuel);
    }
}
