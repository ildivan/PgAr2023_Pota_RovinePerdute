import java.util.ArrayList;
import java.util.List;

/**
 * Represent one of the many cities that the archeologists have to go through.
 * We consider the starting point and the destination as cities.
 */
public class City {
    private int id; // Identification number
    private String name; // City name
    private int x; // Abscissa axis
    private int y; // Ordinate axis
    private int h; // Applicate axis
    private List<City> connections; // List of all cities connected to a city instance

    public List<City> getConnections() {
        return connections;
    }

    public void addConnection(City city) {
        this.connections.add(city);
    }

    public City(int id, String name, int latitude, int longitude, int altitude) {
        this.id = id;
        this.name = name;
        this.x = latitude;
        this.y = longitude;
        this.h = altitude;
        this.connections = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }
    
}
