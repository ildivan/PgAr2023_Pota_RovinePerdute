import java.util.*;

/**
 * Class that implements the A* algorithm.
 */
public class PathFinder {
    private City start;
    private City end;
    private EdgeCalculator calc;
    private Map<City, Double> gCosts;
    private Map<City, Double> hCosts;
    private Map<City,City> previousCities;

    public PathFinder(City start, City end, EdgeCalculator calc) {
        this.start = start;
        this.end = end;
        this.calc = calc;
        gCosts = new HashMap<>();
        hCosts = new HashMap<>();
        previousCities = new HashMap<>();
        calculateOptimalRoute();
    }

    private void calculateOptimalRoute() {
        List<City> openSet = new ArrayList<>();
        List<City> closedSet = new ArrayList<>();

        openSet.add(start);
        gCosts.put(start,0.0);
        hCosts.put(start, getHeuristic(start));

        while(!openSet.isEmpty()){
            City currentCity = openSet.get(0);
            for (City city : openSet) {
                if(
                        getFCost(city) < getFCost(currentCity)
                                || (getFCost(city) == getFCost(currentCity) && gCosts.get(city) < gCosts.get(currentCity))
                ) {
                    currentCity = city;
                }
            }

            openSet.remove(currentCity);
            closedSet.add(currentCity);


            for (City neighbour : currentCity.getConnections()) {
                if(closedSet.contains(neighbour))
                    continue;

                double neighbourGCost = gCosts.get(currentCity) + calc.calculateEdgeDistance(currentCity,neighbour);

                if(!openSet.contains(neighbour) || neighbourGCost < gCosts.get(neighbour)) {
                    gCosts.put(neighbour,neighbourGCost);
                    previousCities.put(neighbour,currentCity);

                    if(!openSet.contains(neighbour)) {
                        hCosts.put(neighbour, getHeuristic(neighbour));
                        openSet.add(neighbour);
                    }
                }
            }
        }
    }

    private double getHeuristic(City city) {
        return calc.calculateEdgeDistance(city,end);
    }

    private double getFCost(City city) {
        return gCosts.get(city) + hCosts.get(city);
    }

    public ArrayDeque<City> getOptimalRoute() {
        ArrayDeque<City> route = new ArrayDeque<>();
        City current = end;
        while(current != start) {
            route.add(current);
            current = previousCities.get(current);
        }
        route.add(start);

        return route;
    }

    public double getPathCost() {
        return gCosts.get(end);
    }

    public int getNumberOfCities() {
        return getOptimalRoute().size();
    }
}
