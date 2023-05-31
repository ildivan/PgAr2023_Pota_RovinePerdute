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

    /**
     * Constructor for the pathfinder.
     * @param start the start node
     * @param end the end node
     * @param calc The object that calculates the cost from a node to another.
     */
    public PathFinder(City start, City end, EdgeCalculator calc) {
        this.start = start;
        this.end = end;
        this.calc = calc;
        gCosts = new HashMap<>();
        hCosts = new HashMap<>();
        previousCities = new HashMap<>();
        calculateOptimalRoute();
    }

    //Travels through the graph and finds the optimal path
    private void calculateOptimalRoute() {
        List<City> openSet = new ArrayList<>(); //List of nodes to process
        List<City> closedSet = new ArrayList<>(); //List of nodes already processed

        //Sets start node information
        openSet.add(start);
        gCosts.put(start,0.0);
        hCosts.put(start, getHCost(start));

        while(!openSet.isEmpty()){

            City currentCity = openSet.get(0);
            //Gets the node in the openSet with the lowest f cost , or the lowest g cost if there are two nodes with
            //the lowest f score.
            for (City city : openSet) {
                if(
                        getFCost(city) < getFCost(currentCity)
                                || (getFCost(city) == getFCost(currentCity) && gCosts.get(city) < gCosts.get(currentCity))
                ) {
                    currentCity = city;
                }
            }

            //Update openSet and closedSet for the currectCity, as currentCity is now considered processed.
            openSet.remove(currentCity);
            closedSet.add(currentCity);


            for (City neighbor : currentCity.getConnections()) {
                if(closedSet.contains(neighbor)) //if a neighbor is in closedSet it should not be considered
                    continue;

                double neighborGCost = gCosts.get(currentCity) + calc.calculateEdgeDistance(currentCity,neighbor);

                //Checks if the neighbor has a lower gCost than it previously had,
                // or if the node was not previously checked, thus not having a g cost.
                if(!openSet.contains(neighbor) || neighborGCost < gCosts.get(neighbor)) {
                    gCosts.put(neighbor,neighborGCost);
                    previousCities.put(neighbor,currentCity);

                    //if the node was not previously checked, the h cost of the  node is generated and the node
                    //is put in the openSet
                    if(!openSet.contains(neighbor)) {
                        hCosts.put(neighbor, getHCost(neighbor));
                        openSet.add(neighbor);
                    }
                }
            }
        }
    }

    //Get heuristic cost of a city
    private double getHCost(City city) {
        return calc.calculateEdgeDistance(city,end);
    }

    //Get estimated cost of a node, fcost = gcost + hcost
    private double getFCost(City city) {
        return gCosts.get(city) + hCosts.get(city);
    }

    /**
     * Returns the optimal route as a stack, with the start node on top.
     */
    public ArrayDeque<City> getOptimalRoute() {
        ArrayDeque<City> route = new ArrayDeque<>(); //Stack representing a path

        City current = end;
        while(current != start) {
            route.add(current);
            current = previousCities.get(current);
        }
        route.add(start);

        return route;
    }

    /**
     * Returns the total cost of the optimal path.
     */
    public double getPathCost() {
        return gCosts.get(end);
    }

    /**
     * Returns length of the path, the number of cities to go from start to end.
     */
    public int getNumberOfCities() {
        return getOptimalRoute().size();
    }
}
