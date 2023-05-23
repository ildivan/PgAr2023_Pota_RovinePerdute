import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements the A* algorithm.
 */
public class PathFinder {
    private City start;
    private City end;
    private EdgeCalculator calc;
    private Map<City, Double> minimumCosts;
    private Map<City,City> previousCities;

    public PathFinder(City start, City end, EdgeCalculator calc) {
        this.start = start;
        this.end = end;
        this.calc = calc;
        minimumCosts = new HashMap<>();
        previousCities = new HashMap<>();
    }

    public ArrayDeque<City> getOptimalRoute() {

        return new ArrayDeque<>();
    }

    private void getOptimalRoute(City city) {
        Map<City,Double> costs = new HashMap<>();

        for (City next : city.getConnections()) {
            double cost = calc.calculateEdgeDistance(city,next) + getHeuristic(next);
            if(minimumCosts.get(next) == null && cost < minimumCosts.get(next)) {
                minimumCosts.put(next,cost);
            }
            costs.put(next,cost);
        }
    }

    private int getHeuristic(City city) {
        int deltaX = (int) Math.pow(city.getX() - end.getX(), 2);
        int deltaY = (int) Math.pow(city.getY() - end.getY(), 2);
        int deltaH = (int) Math.pow(city.getH() - end.getH(),2);
        return (int) Math.sqrt(deltaY + deltaX + deltaH);
    }
}
