import java.util.List;

public class City {
    private int id;
    private String name;
    private int x;
    private int y;
    private int h;
    private List<City> connections;

    public List<City> getConnections() {
        return connections;
    }

    public void setConnections(List<City> connections) {
        this.connections = connections;
    }

    public City(int id, String name, int latitude, int longitude, int altitude) {
        this.id = id;
        this.name = name;
        this.x = latitude;
        this.y = longitude;
        this.h = altitude;
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
