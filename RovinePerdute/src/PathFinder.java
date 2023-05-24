import java.util.*;

/**
 * Class that implements the A* algorithm.
 */
public class PathFinder {
    private boolean alreadySolved;
    private City start;
    private City end;
    private EdgeCalculator calc;
    private Map<City, Double> gCosts;
    private Map<City, Double> hCosts;
    private Map<City,City> previousCities;

    public PathFinder(City start, City end, EdgeCalculator calc) {
        alreadySolved = false;
        this.start = start;
        this.end = end;
        this.calc = calc;
        gCosts = new HashMap<>();
        hCosts = new HashMap<>();
        previousCities = new HashMap<>();
    }

    public ArrayDeque<City> getOptimalRoute() {
        if(!alreadySolved){
            List<City> toSearch = new ArrayList<>();
            List<City> processed = new ArrayList<>();
            toSearch.add(start);

            while(!toSearch.isEmpty()){
                gCosts.put(start,0.0);
                hCosts.put(start, getHeuristic(start));

                City currentCity = toSearch.get(0);
                for (City city : toSearch) {
                    if(
                            getFCost(city) < getFCost(currentCity)
                            || (getFCost(city) == getFCost(currentCity) && gCosts.get(city) < gCosts.get(currentCity))
                    ) {
                        currentCity = city;
                    }
                }

                toSearch.remove(currentCity);
                processed.add(currentCity);


                List<City> remainingNeighbors
                        = currentCity.getConnections().stream().filter((x) -> !processed.contains(x)).toList();

                for (City next : remainingNeighbors) {
                    double cost = gCosts.get(currentCity) + calc.calculateEdgeDistance(currentCity,next);
                    if(gCosts.get(next) == null || cost < gCosts.get(next)) {
                        gCosts.put(next,cost);
                        previousCities.put(next,currentCity);

                        if(gCosts.get(next) == null) {
                            hCosts.put(next, getHeuristic(next));
                            toSearch.add(next);
                        }
                    }
                }
                alreadySolved = true;
            }
        }

        ArrayDeque<City> route = new ArrayDeque<>();
        City current = end;
        while(current != start) {
            route.add(current);
            current = previousCities.get(current);
        }
        route.add(start);

        return route;
    }

    private double getFCost(City city) {
        return gCosts.get(city) + hCosts.get(city);
    }

    private double getHeuristic(City city) {
        double deltaX = Math.pow(city.getX() - end.getX(), 2);
        double deltaY = Math.pow(city.getY() - end.getY(), 2);
        double deltaH = Math.pow(city.getH() - end.getH(),2);
        return Math.sqrt(deltaY + deltaX + deltaH);
    }
}
